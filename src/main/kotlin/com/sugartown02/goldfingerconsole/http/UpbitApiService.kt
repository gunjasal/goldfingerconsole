package com.sugartown02.goldfingerconsole.http

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sugartown02.goldfingerconsole.domain.MarketCode
import com.sugartown02.goldfingerconsole.domain.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

interface UpbitApiService {
    @GET("/v1/market/all")
    fun marketAll(): Call<Markets>

    @GET("/v1/ticker")
    fun ticker(@Query("markets") market: String): Call<Tickers>

    @GET("/v1/orderbook")
    fun orderbook(@Query("markets") market: String): Call<OrderBooks>

    @GET("/v1/orders")
    fun orders(@Header("Authorization") authToken: String, @QueryMap queryMap: Map<String, String>): Call<Orders>

    @GET("/v1/orders/chance")
    fun orderChance(@Header("Authorization") authToken: String, @QueryMap queryMap: Map<String, String>): Call<OrderChance>
}
