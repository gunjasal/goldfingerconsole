package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.declaration.PriceUnit
import com.sugartown02.goldfingerconsole.declaration.guide
import com.sugartown02.goldfingerconsole.declaration.isZero
import com.sugartown02.goldfingerconsole.domain.InputValidity
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.helper.OrderCalculator
import com.sugartown02.goldfingerconsole.domain.helper.Price
import com.sugartown02.goldfingerconsole.http.UpbitApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MaxPriceInputService: AbstractOrderService<PriceUnit, Double>() {
    @Autowired lateinit var upbitApiClient: UpbitApiClient

    override fun fetchOptions(orderBuilder: OrderBuilder): PriceUnit? {
        val ticker = upbitApiClient.ticker(orderBuilder.market!!.code)
        return OrderCalculator.priceUnit(ticker!![0].tradePrice)
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: PriceUnit) {
        input("${orderBuilder.state.guide} (${options.guide()})")
    }

    override fun showEmptyOptionGuide(orderBuilder: OrderBuilder) {
        throw NotImplementedError("의도된 노 구현")
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<Double> {
        return ConsoleInput.DoubleInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<Double>, options: PriceUnit): InputValidity {
        return if ((input.translation!! > orderBuilder.minPrice.toDouble()) &&
                input.translation.toBigDecimal().rem(options.toBigDecimal()).isZero()) InputValidity.VALID_Y
        else InputValidity.INVALID
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<Double>, options: PriceUnit) {
        orderBuilder.maxPrice = Price(input.translation!!.toString())
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("₩ %,.1f\n".format(orderBuilder.maxPrice))
    }
}
