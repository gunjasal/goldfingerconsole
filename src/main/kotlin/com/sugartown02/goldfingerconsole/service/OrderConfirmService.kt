package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.EmptyOption
import com.sugartown02.goldfingerconsole.domain.InputValidity
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderConfirmService: AbstractOrderService<EmptyOption, String>() {
    override fun fetchOptions(orderBuilder: OrderBuilder): EmptyOption? {
        return EmptyOption()
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: EmptyOption) {
        guide(orderBuilder.summary())
        input(orderBuilder.state.guide)
    }

    override fun showEmptyOptionGuide(orderBuilder: OrderBuilder) {
        throw NotImplementedError("의도된 노 구현")
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<String> {
        return ConsoleInput.StringInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: EmptyOption): InputValidity {
        return if (input.translation!!.trim() == "Y") InputValidity.VALID_Y
        else if (input.translation.trim() == "n") InputValidity.VALID_N
        else InputValidity.INVALID
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: EmptyOption) {
        orderBuilder.orders = orderBuilder.orderUnits()
        // todo execute order
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("${orderBuilder.orderUnits().mapIndexed { idx, orderUnit -> "($idx) ${orderUnit.postExecutionSummary}\n" }}")
    }
}
