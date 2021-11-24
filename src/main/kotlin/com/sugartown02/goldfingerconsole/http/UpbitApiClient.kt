package com.sugartown02.goldfingerconsole.http

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sugartown02.goldfingerconsole.declaration.logger
import com.sugartown02.goldfingerconsole.domain.MarketCode
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.helper.OrderUnits
import com.sugartown02.goldfingerconsole.domain.model.*
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.FileInputStream
import java.lang.RuntimeException
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

@Service
class UpbitApiClient {
    companion object {
        val log = logger()
        private val objectMapper =
            jacksonObjectMapper().enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
        private val httpClient = OkHttpClient()

        private val upbitApiClient = Retrofit.Builder().baseUrl("https://api.upbit.com")
            .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(httpClient).build()

        private val upbitApiService = upbitApiClient.create(UpbitApiService::class.java)

        private fun keyProperties(): Properties {
            val props = Properties()
            props.load(FileInputStream("./keys.properties"))

            if (props["accessToken"] == null || props["accessToken"].toString().isBlank()) throw IllegalStateException("no accessToken in keys.properties")
            if (props["secretToken"] == null || props["secretToken"].toString().isBlank()) throw IllegalStateException("no secretToken in keys.properties")

            return props
        }

        private fun upbitToken(queryMap: Map<String, String>): String {
            val keys = keyProperties()
            val queryString = queryMap.entries.joinToString(separator = "&") { "${it.key}=${it.value}" }

            val md: MessageDigest = MessageDigest.getInstance("SHA-512")
            md.update(queryString.toByteArray(charset("UTF-8")))
            val queryHash = String.format("%0128x", BigInteger(1, md.digest()))

            val algorithm: Algorithm = Algorithm.HMAC256(keys["secretToken"].toString())
            val jwtToken: String = JWT.create()
                .withClaim("access_key", keys["accessToken"].toString())
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm)

            return "Bearer $jwtToken"
        }
    }

    fun marketAll(): Markets? = call(upbitApiService.marketAll(), "market all api error")

    fun ticker(marketCode: MarketCode): Tickers? = call(upbitApiService.ticker(marketCode.id), "ticker api error")

    fun orderBook(marketCode: MarketCode): OrderBooks? = call(upbitApiService.orderbook(marketCode.id), "orderbook api error")

    fun getOrders(marketCode: MarketCode? = null): Orders? {
        val queryMap = mutableMapOf<String, String>()
        queryMap["state"] = "wait"
        queryMap["order_by"] = "desc"
        if (marketCode != null) queryMap["market"] = marketCode.id

        return call(upbitApiService.orders(upbitToken(queryMap), queryMap), "orders api error")
    }

    fun orderChance(marketCode: MarketCode): OrderChance? {
        val queryMap = mutableMapOf<String, String>()
        queryMap["market"] = marketCode.id

        return call(upbitApiService.orderChance(upbitToken(queryMap), queryMap), "order chance api error")
    }

    fun requestOrder(orderBuilder: OrderBuilder): List<Order> {
        return orderBuilder.orderUnits().map { orderUnit ->
            val queryMap = mutableMapOf<String, String>()
            queryMap["market"] = orderBuilder.market!!.code.id
            queryMap["side"] = orderBuilder.side!!.upbitParam
            queryMap["price"] = orderUnit.price.toString()
            queryMap["volume"] = orderUnit.quantity.toString()
            queryMap["ord_type"] = "limit"

            try {
                Thread.sleep(130) // 8회/1초
                call(upbitApiService.requestOrder(upbitToken(queryMap), queryMap), "request order api error")
            } catch (e: Exception) {
                Order(
                    market = orderBuilder.market!!.code.id,
                    side = orderBuilder.side!!.upbitParam,
                    price = orderUnit.price.toString(),
                    volume = orderUnit.quantity.toString(),
                    customErrorMessage = "${e.message}")
            }
        }

    }

    fun cancelOrder(uuids: List<String>): List<CancelledOrder> {
        return uuids.map { uuid ->
            val queryMap = mutableMapOf<String, String>()
            queryMap["uuid"] = uuid

            try {
                Thread.sleep(130) // 8회/1초
                call(upbitApiService.cancelOrder(upbitToken(queryMap), queryMap), "order cancel api error")
            } catch (e: Exception) {
                CancelledOrder(uuid = uuid, customErrorMessage = "${e.message}")
            }
        }
    }

    private fun <T> call(call: Call<T>, errorMessage: String): T {
        val response = call.execute()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            log.error("${errorMessage}: ${response.message()}")
            throw RuntimeException(response.message())
        }
    }
}
