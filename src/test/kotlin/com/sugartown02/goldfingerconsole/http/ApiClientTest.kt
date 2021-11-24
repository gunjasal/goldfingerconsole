package com.sugartown02.goldfingerconsole.http

import com.sugartown02.goldfingerconsole.domain.MarketCode
import com.sugartown02.goldfingerconsole.domain.model.list
import com.sugartown02.goldfingerconsole.domain.model.print
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ApiClientTest {
    private val apiClient = UpbitApiClient()

    @Test
    fun `test unknown market`() {
        val markets = apiClient.marketAll()
        assertTrue(markets!!.list().stream().anyMatch { e -> e.code == MarketCode.UNKNOWN })

        markets.print(true)
    }

    @Test
    fun `test ticker`() {
        val marketCode = MarketCode.KRW_ETH
        val ticker = apiClient.ticker(marketCode)!!

        Assertions.assertThat(ticker[0].market).isEqualTo(marketCode.id)
        println(ticker)
    }

    @Test
    fun `test order book`() {
        val marketCode = MarketCode.KRW_ETH
        val orderBook = apiClient.orderBook(marketCode)!!

        Assertions.assertThat(orderBook[0].market).isEqualTo(marketCode.id)
        println(orderBook)
    }

    @Test
    fun `test orders api`() {
        val orders = apiClient.getOrders()!!
        println(orders)
    }

    @Test
    fun `test order chance`() {
        val marketCode = MarketCode.KRW_ETH
        val orderChance = apiClient.orderChance(marketCode)!!

        Assertions.assertThat(orderChance.market.id).isEqualTo(marketCode.id)
        println(orderChance)
    }
}
