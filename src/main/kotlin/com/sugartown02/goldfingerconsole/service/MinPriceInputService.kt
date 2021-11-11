package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.declaration.PriceUnit
import com.sugartown02.goldfingerconsole.declaration.guide
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.helper.OrderCalculator
import com.sugartown02.goldfingerconsole.domain.helper.Price
import com.sugartown02.goldfingerconsole.http.UpbitApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class MinPriceInputService: AbstractOrderService<PriceUnit, Double>() {
    @Autowired lateinit var upbitApiClient: UpbitApiClient

    override fun fetchOptions(orderBuilder: OrderBuilder): PriceUnit? {
        val ticker = upbitApiClient.ticker(orderBuilder.market!!.code)
        return OrderCalculator.priceUnit(ticker!![0].tradePrice)
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: PriceUnit) {
        input("${orderBuilder.state.guide} (${options.guide()})")
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<Double> {
        return ConsoleInput.DoubleInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<Double>, options: PriceUnit): Boolean {
        return BigDecimal(input.translation.toString()).rem(BigDecimal(options.toString())) == BigDecimal("0.0")
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<Double>, options: PriceUnit) {
        orderBuilder.minPrice = Price(input.translation!!.toString())
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("₩ %,.1f\n".format(orderBuilder.minPrice))
    }
}