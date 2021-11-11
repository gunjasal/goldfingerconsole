package com.sugartown02.goldfingerconsole.domain

import com.sugartown02.goldfingerconsole.declaration.step
import com.sugartown02.goldfingerconsole.domain.helper.OrderUnit
import com.sugartown02.goldfingerconsole.domain.helper.OrderUnits
import com.sugartown02.goldfingerconsole.domain.helper.Price
import com.sugartown02.goldfingerconsole.domain.model.Market
import java.math.RoundingMode

class OrderBuilder {
    var type: OrderType? = null
    var state: OrderState = OrderState.CHOOSE_TYPE
    var market: Market? = null
    var side: OrderSide? = null
    var splitType: SplitType? = null
    var money: Price = Price("0.0")
    var minPrice: Price = Price("0.0")
    var maxPrice: Price = Price("0.0")
    var priceUnit: Price = Price("0.0")
    var orders: OrderUnits? = null

    // publics
    fun splitMoneyToOrderUnits(): OrderUnits {
        return when (splitType) {
            SplitType.PYRAMID -> return pyramidSplit()
            SplitType.EQUAL -> return equalSplit()
            null -> emptyList()
        }
    }

    fun orderUnits(): OrderUnits = splitMoneyToOrderUnits()

    private fun orderPrices(): List<Price> {
        return when (side) {
            OrderSide.ASK -> minPrice.rangeTo(maxPrice).step(priceUnit).toList()
            OrderSide.BID -> minPrice.rangeTo(maxPrice).step(priceUnit).toList().reversed()
            null -> emptyList()
        }
    }

    fun init() = run {
        type = null
        state = OrderState.CHOOSE_ORDER_MARKET
        market = null
        side = null
        splitType = null
        money = Price("0.0")
        minPrice = Price("0.0")
        maxPrice = Price("0.0")
        priceUnit = Price("0.0")
        orders = null
    }

    fun reprice() = run {
        state = OrderState.INPUT_MIN_PRICE
        money = Price("0.0")
        minPrice = Price("0.0")
        maxPrice = Price("0.0")
        priceUnit = Price("0.0")
    }

    fun min() = run { // todo
        state = OrderState.INPUT_MIN_PRICE
        minPrice = Price("0.0")
        maxPrice = Price("0.0")
        priceUnit = Price("0.0")
    }

    // privates
    private fun equalSplit(): OrderUnits {
        val prices = orderPrices()

        return prices.map { price ->
            val ratio = 1.toBigDecimal().divide(prices.size.toBigDecimal(), 5, RoundingMode.HALF_UP)
            val moneyByRatio = ratio.times(money)
            val quantity = moneyByRatio.divide(price, 5, RoundingMode.HALF_UP)
            OrderUnit(price, quantity)
        }.toList()
    }

    private fun pyramidSplit(): OrderUnits {
        val prices: List<Price> = orderPrices()
        val priceIndexSum = ((prices.size * (prices.size + 1)) / 2).toBigDecimal()

        return prices.mapIndexed { idx, price ->
            val ratio = (idx + 1).toBigDecimal().divide(priceIndexSum, 5, RoundingMode.HALF_UP)
            val moneyByRatio = ratio.times(money)
            val quantity = moneyByRatio.divide(price, 5, RoundingMode.HALF_UP)
            OrderUnit(price, quantity)
        }.toList()
    }

    // info
    fun summary() = "=======================\n" +
            "- ${type?.koText}\n" +
            "- ${market?.code?.code}\n" +
            "- ${side?.koText} (${side?.upbitParam})\n" +
            "- ${splitType?.koText}\n" +
            "- ${"총 ₩ %,.1f".format(money)}\n" +
            "- ${"최소 ₩ %,.1f".format(minPrice)}\n" +
            "- ${"최대 ₩ %,.1f".format(maxPrice)}\n" +
            "- ${"단위 ₩ %,.1f".format(priceUnit)}\n" +
            "${orderUnits().mapIndexed { idx, orderUnit -> "($idx) ${orderUnit.preExecutionSummary}\n" }}" +
            "======================="
}
