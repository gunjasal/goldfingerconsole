package com.sugartown02.goldfingerconsole.domain

enum class SplitType(val command: String, val alias: String, val koText: String) {
    PYRAMID("pyramid", "1", "피라미드분할"),
    EQUAL("equal", "2","균등분할");

    companion object {
        private val types = values().associateBy(SplitType::alias)
        fun from(alias: String) = types[alias] ?: null
    }
}