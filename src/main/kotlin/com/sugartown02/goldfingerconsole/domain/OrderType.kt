package com.sugartown02.goldfingerconsole.domain

enum class OrderType(val command: String, val alias: String, val koText: String) {
    ORDER("order", "1", "주문"),
    CANCEL("cancel", "2", "주문취소"),
    ;

    companion object {
        private val sides = values().associateBy(OrderType::alias)
        fun from(alias: String) = sides[alias] ?: null
    }
}
