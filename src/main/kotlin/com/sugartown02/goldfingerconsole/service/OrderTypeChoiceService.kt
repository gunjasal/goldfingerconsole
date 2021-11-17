package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.OrderType
import org.springframework.stereotype.Service
import java.util.*
import kotlin.streams.toList

@Service
class OrderTypeChoiceService: AbstractOrderService<List<OrderType>, String>() {
    override fun fetchOptions(orderBuilder: OrderBuilder): List<OrderType>? {
        return OrderType.values().toList()
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: List<OrderType>) {
        guide("어서오세오")
        input("${orderBuilder.state.guide} [${options.stream().map { "(${it.alias}) ${it.command}" }.toList().joinToString()}]")
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<String> {
        return ConsoleInput.StringInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: List<OrderType>): Boolean {
        return OrderType.from(input.translation!!) != null
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: List<OrderType>) {
        orderBuilder.type = OrderType.from(input.translation!!)
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("${orderBuilder.type!!.koText} 골랏읍니다\n")
    }
}
