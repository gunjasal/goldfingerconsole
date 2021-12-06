package com.sugartown02.goldfingerconsole.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.sugartown02.goldfingerconsole.domain.MarketCode

typealias Markets = List<Market>

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Market (@JsonProperty("market") val code: MarketCode, val koreanName: String, val englishName: String) {
    val info get() = "$koreanName($code)"
}

fun Markets.list(activeOnly: Boolean = false): Markets {
    return this.filter { it.code != MarketCode.UNKNOWN }
        .filter { (activeOnly && it.code.active) || (!activeOnly) }
        .sortedBy { it.code.priority }
        .sortedBy { !it.code.active }
}

fun Markets.print(activeOnly: Boolean = false) {
    this.list(activeOnly).forEachIndexed { idx, market -> println("($idx) ${market.info}") }
}
