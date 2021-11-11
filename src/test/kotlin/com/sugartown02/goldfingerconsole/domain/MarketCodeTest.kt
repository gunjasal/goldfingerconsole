package com.sugartown02.goldfingerconsole.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MarketCodeTest {
    @Test
    fun `get enum from code`() {
        assertThat(MarketCode.from("KRW-BTC")).isEqualTo(MarketCode.KRW_BTC)
    }

    @Test
    fun `get unknown enum`() {
        assertThat(MarketCode.from("sdfasdf")).isEqualTo(MarketCode.UNKNOWN)
    }
}