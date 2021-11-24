package com.sugartown02.goldfingerconsole.domain.helper

import java.math.BigDecimal

typealias Price = BigDecimal
typealias Quantity = BigDecimal
typealias OrderUnits = List<OrderUnit>

data class OrderUnit(val price: Price, val quantity: Quantity) {
    companion object {
        private const val minBidMoney = 5_000
    }

    private val total = (price * quantity).toInt()
    private val valid = total > minBidMoney

    fun preExecutionSummary() = "$price (${"%.5f".format(quantity)} unit) [₩ ${total}] ${if(valid) "유효" else "패스 XXX"}"
}