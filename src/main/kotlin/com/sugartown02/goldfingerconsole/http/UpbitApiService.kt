package com.sugartown02.goldfingerconsole.http

import com.sugartown02.goldfingerconsole.domain.model.*
import retrofit2.Call
import retrofit2.http.*

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

    @POST("/v1/orders")
    fun requestOrder(@Header("Authorization") authToken: String, @QueryMap queryMap: Map<String, String>): Call<Order>

    @DELETE("/v1/order")
    fun cancelOrder(@Header("Authorization") authToken: String, @QueryMap queryMap: Map<String, String>): Call<CancelledOrder>

}
