package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.EmptyOption
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

    override fun scanInput(scanner: Scanner): ConsoleInput<String> {
        return ConsoleInput.StringInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: EmptyOption): Boolean {
        return input.translation!!.trim() == "Y"
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: EmptyOption) {
        orderBuilder.orders = orderBuilder.orderUnits()
        // todo execute order
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("${orderBuilder.orderUnits().mapIndexed { idx, orderUnit -> "($idx) ${orderUnit.postExecutionSummary}\n" }}")
    }
}