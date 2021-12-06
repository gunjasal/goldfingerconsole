package com.sugartown02.goldfingerconsole.domain

enum class OrderSide(val command: String, val alias: String, val upbitParam: String, val koText: String) {
    BID("buy", "1", "bid", "매수(삽니다)"),
    ASK("sell", "2", "ask", "매도(팝니다)"),
    ;

    companion object {
        private val sides = values().associateBy(OrderSide::alias)
        fun from(alias: String) = sides[alias]
    }
}