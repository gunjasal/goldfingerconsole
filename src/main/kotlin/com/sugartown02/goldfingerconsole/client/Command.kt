package com.sugartown02.goldfingerconsole.client

enum class Command(val input: String) {
    INIT("/init"),
    BYE("/bye"),
    HELP("/help");

    companion object {
        private val commands = values().associateBy(Command::input)
        fun from(input: String) = commands[input.trim().lowercase()]
    }
}
