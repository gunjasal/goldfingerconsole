package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.InputValidity
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.OrderSide
import org.springframework.stereotype.Service
import java.util.*
import kotlin.streams.toList

@Service
class OrderSideChoiceService: AbstractOrderService<List<OrderSide>, String>() {
    override fun fetchOptions(orderBuilder: OrderBuilder): List<OrderSide>? {
        return OrderSide.values().toList()
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: List<OrderSide>) {
        input("${orderBuilder.state.guide} [${options.stream().map { "(${it.alias}) ${it.command}" }.toList().joinToString()}]")
    }

    override fun showEmptyOptionGuide(orderBuilder: OrderBuilder) {
        throw NotImplementedError("의도된 노 구현")
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<String> {
        return ConsoleInput.StringInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: List<OrderSide>): InputValidity {
        return if (OrderSide.from(input.translation!!) != null) InputValidity.VALID_Y else InputValidity.INVALID
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: List<OrderSide>) {
        orderBuilder.side = OrderSide.from(input.translation!!)
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("${orderBuilder.side!!.koText} [${orderBuilder.type!!.koText}] 골랏읍니다\n")
    }
}
