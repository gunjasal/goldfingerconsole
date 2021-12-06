package com.sugartown02.goldfingerconsole.domain

import com.sugartown02.goldfingerconsole.domain.helper.Price
import org.junit.jupiter.api.Test

internal class OrderBuilderTest {
    @Test
    fun `upward equal split`() {
        val orderBuilder = OrderBuilder()
        orderBuilder.priceUnit = Price("100.0")
        orderBuilder.money = Price("500000.0")
        orderBuilder.minPrice = Price("11000.0")
        orderBuilder.maxPrice = Price("11500.0")
        orderBuilder.side = OrderSide.BID
        orderBuilder.splitType = SplitType.EQUAL

        orderBuilder.splitMoneyToOrderUnits().forEachIndexed { idx, orderUnit ->
            println("($idx) ${orderUnit.preExecutionSummary}")
        }
    }

    @Test
    fun `downward equal split`() {
        val orderBuilder = OrderBuilder()
        orderBuilder.priceUnit = Price("1.0")
        orderBuilder.money = Price("500000.0")
        orderBuilder.minPrice = Price("465.0")
        orderBuilder.maxPrice = Price("530.0")
        orderBuilder.side = OrderSide.ASK
        orderBuilder.splitType = SplitType.EQUAL

        orderBuilder.splitMoneyToOrderUnits().forEachIndexed { idx, orderUnit ->
            println("($idx) ${orderUnit.preExecutionSummary}")
        }
    }

    @Test
    fun `upward pyramid split`() {
        val orderBuilder = OrderBuilder()
        orderBuilder.priceUnit = Price("100.0")
        orderBuilder.money = Price("500000.0")
        orderBuilder.minPrice = Price("11000.0")
        orderBuilder.maxPrice = Price("11500.0")
        orderBuilder.side = OrderSide.BID
        orderBuilder.splitType = SplitType.PYRAMID

        orderBuilder.splitMoneyToOrderUnits().forEachIndexed { idx, orderUnit ->
            println("($idx) ${orderUnit.preExecutionSummary}")
        }
    }

    @Test
    fun `downward pyramid split`() {
        val orderBuilder = OrderBuilder()
        orderBuilder.priceUnit = Price("1.0")
        orderBuilder.money = Price("500000.0")
        orderBuilder.minPrice = Price("465.0")
        orderBuilder.maxPrice = Price("530.0")
        orderBuilder.side = OrderSide.ASK
        orderBuilder.splitType = SplitType.PYRAMID

        orderBuilder.splitMoneyToOrderUnits().forEachIndexed { idx, orderUnit ->
            println("($idx) ${orderUnit.preExecutionSummary}")
        }
    }

}