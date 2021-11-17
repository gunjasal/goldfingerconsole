package com.sugartown02.goldfingerconsole.client

import com.sugartown02.goldfingerconsole.domain.OrderState

enum class Command(val input: String, val nextState: OrderState?) {
    INIT("/init", OrderState.CHOOSE_TYPE),
    MONEY("/money", OrderState.INPUT_TOTAL_MONEY),
    BYE("/bye", OrderState.BYE),
    HELP("/help", null);

    companion object {
        private val commands = values().associateBy(Command::input)
        fun from(input: String) = commands[input.trim().lowercase()] ?: null
    }
}
