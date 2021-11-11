package com.sugartown02.goldfingerconsole.domain.helper

import java.math.BigDecimal

typealias Price = BigDecimal
typealias Quantity = BigDecimal
typealias OrderUnits = List<OrderUnit>

data class OrderUnit(val price: Price, val quantity: Quantity) {
    companion object {
        private const val minBidMoney = 5_000
    }

    val executed = false

    private val total = (price * quantity).toInt()
    private val valid = total > minBidMoney
    val preExecutionSummary = "$price (${"%.5f".format(quantity)} unit) [₩ ${total}] ${if(valid) "유효" else "패스 XXX"}"
    val postExecutionSummary = "$preExecutionSummary [${if(executed) "주문 완료" else "주문 실패"}]"
}