package com.sugartown02.goldfingerconsole.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

typealias Tickers = List<Ticker>

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Ticker (
    val market: String,
    val tradeDate: String,
    val tradeTime: String,
    val tradeDateKst: String,
    val tradeTimeKst: String,
    val tradeTimestamp: Long,
    val openingPrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val tradePrice: Double,
    val prevClosingPrice: Double,
    val change: String,
    val changePrice: Double,
    val changeRate: Double,
    val signedChangePrice: Double,
    val signedChangeRate: Double,
    val tradeVolume: Double,
    val accTradePrice: Double,
    @JsonProperty("acc_trade_price_24h") val accTradePrice24H: Double,
    val accTradeVolume: Double,
    @JsonProperty("acc_trade_volume_24h") val accTradeVolume24H: Double,
    @JsonProperty("highest_52_week_price") val highest52WeekPrice: Double,
    @JsonProperty("highest_52_week_date") val highest52WeekDate: String,
    @JsonProperty("lowest_52_week_price") val lowest52WeekPrice: Double,
    @JsonProperty("lowest_52_week_date")  val lowest52WeekDate: String,
    val timestamp: Long
)

