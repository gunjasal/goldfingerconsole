package com.sugartown02.goldfingerconsole.client

sealed class ConsoleInput<T>(value: String) {
    class IntInput(value: String): ConsoleInput<Int>(value)
    class DoubleInput(value: String): ConsoleInput<Double>(value)
    class StringInput(value: String): ConsoleInput<String>(value)

    val command = Command.from(value)
    val translation: T? = command?.let {
        null
    } ?: run {
        when (this) {
            is IntInput -> value.toIntOrNull() as T?
            is DoubleInput -> {
                value.toDoubleOrNull()?.let {
                    it
                } as T? ?: {
                    value.toIntOrNull()?.let { i ->
                        i.toDouble()
                    }
                } as T?
            }
            is StringInput -> value as T?
        }
    }
}
