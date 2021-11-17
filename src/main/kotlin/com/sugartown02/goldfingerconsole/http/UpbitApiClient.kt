package com.sugartown02.goldfingerconsole.http

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sugartown02.goldfingerconsole.declaration.logger
import com.sugartown02.goldfingerconsole.domain.MarketCode
import com.sugartown02.goldfingerconsole.domain.model.*
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.FileInputStream
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

    fun marketAll(): Markets? {
        return try {
            return upbitApiService.marketAll().execute().body()
        } catch (e: Exception) { // todo remove catch blocks below
            log.error("market all api call error: ", e)
            throw e
        }
    }

    fun ticker(marketCode: MarketCode): Tickers? {
        return try {
            return upbitApiService.ticker(marketCode.id).execute().body()
        } catch (e: Exception) {
            log.error("ticker api call error: ", e)
            throw e
        }
    }

    fun orderBook(marketCode: MarketCode): OrderBooks? {
        return try {
            return upbitApiService.orderbook(marketCode.id).execute().body()
        } catch (e: Exception) {
            log.error("order book api call error: ", e)
            throw e
        }
    }

    fun orders(): Orders? {
        val queryMap = mutableMapOf<String, String>()
        queryMap["state"] = "wait"

        return try {
            return upbitApiService.orders(upbitToken(queryMap), queryMap).execute().body()
        } catch (e: Exception) {
            log.error("orders api call error: ", e)
            throw e
        }
    }

    fun orderChance(marketCode: MarketCode): OrderChance? {
        val queryMap = mutableMapOf<String, String>()
        queryMap["market"] = marketCode.id

        return try {
            return upbitApiService.orderChance(upbitToken(queryMap), queryMap).execute().body()
        } catch (e: Exception) {
            log.error("order chance api call error: ", e)
            throw e
        }
    }
}
