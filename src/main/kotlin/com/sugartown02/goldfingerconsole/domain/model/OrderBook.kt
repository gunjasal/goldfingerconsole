package com.sugartown02.goldfingerconsole.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

typealias OrderBooks = List<OrderBook>

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderBook(
    val market: String,
    val timestamp: Long,
    val totalAskSize: Double,
    val totalBidSize: Double,
    val orderbookUnits: List<OrderBookUnit>?
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderBookUnit(
    val askPrice: Double,
    val bidPrice: Double,
    val askSize: Double,
    val bidSize: Double
)
