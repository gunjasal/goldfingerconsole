package com.sugartown02.goldfingerconsole.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.sugartown02.goldfingerconsole.domain.OrderSide

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderChance (
    val bidFee: String,
    val askFee: String,
    val market: MarketSummary,
    val bidAccount: AccountState,
    val askAccount: AccountState
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class MarketSummary (
    val id: String,
    val name: String,
    val orderTypes: List<String>,
    val orderSides: List<String>,
    val bid: OrderConstraint,
    val ask: OrderConstraint,
    val maxTotal: String,
    val state: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AccountState (
    val currency: String,
    val balance: String,
    val locked: String,
    val avgBuyPrice: String,
    val avgBuyPriceModified: Boolean,
    val unitCurrency: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class OrderConstraint (
    val currency: String,
    val priceUnit: Int = null ?: 1,
    val minTotal: Double
)
