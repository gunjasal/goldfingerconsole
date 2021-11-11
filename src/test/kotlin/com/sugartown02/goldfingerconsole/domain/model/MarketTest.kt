package com.sugartown02.goldfingerconsole.domain.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sugartown02.goldfingerconsole.domain.MarketCode
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MarketTest {
    @Test
    fun `deserialize by code`() {
        val json = """
            [{
              "market": "KRW-BTC",
              "korean_name": "비트코인",
              "english_name": "Bitcoin"
            }, {
              "market": "KRW-ETH",
              "korean_name": "이더리움",
              "english_name": "Ethereum"
            }]
        """.trimIndent()

        val markets = jacksonObjectMapper().readValue<Markets>(json)

        assertThat(markets.first().code).isEqualTo(MarketCode.KRW_BTC)
        assertThat(markets.last().code).isEqualTo(MarketCode.KRW_ETH)
    }
}
