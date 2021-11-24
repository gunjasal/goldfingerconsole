package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.EmptyOption
import com.sugartown02.goldfingerconsole.domain.InputValidity
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.helper.Price
import org.springframework.stereotype.Service
import java.util.*

@Service
class TotalMoneyInputService: AbstractOrderService<EmptyOption, Int>() {
    override fun fetchOptions(orderBuilder: OrderBuilder): EmptyOption? {
        return EmptyOption()
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: EmptyOption) {
        input("${orderBuilder.state.guide}")
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<Int> {
        return ConsoleInput.IntInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<Int>, options: EmptyOption): InputValidity {
        return if (input.translation!! >= 5000) InputValidity.VALID_Y else InputValidity.INVALID
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<Int>, options: EmptyOption) {
        orderBuilder.money = Price(input.translation!!.toString())
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("₩ %,.1f\n".format(orderBuilder.money))
    }
}