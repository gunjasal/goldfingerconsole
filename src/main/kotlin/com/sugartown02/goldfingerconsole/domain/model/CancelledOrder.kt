package com.sugartown02.goldfingerconsole.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class CancelledOrder(
    val uuid: String,
    val side: String? = "",
    val ordType: String? = "",
    val price: String? = "",
    val state: String? = "",
    val market: String? = "",
    val createdAt: String? = "",
    val volume: String? = "",
    val remainingVolume: String? = "",
    val reservedFee: String? = "",
    val remainingFee: String? = "",
    val paidFee: String? = "",
    val locked: String? = "",
    val executedVolume: String? = "",
    val tradeCount: String? = "",

    val customErrorMessage: String? // not in original model
) {
    fun compact() = "[$state] $market ($side) $price 수량($volume) 잔여수량($remainingVolume) ${customErrorMessage ?: ""}"
}
