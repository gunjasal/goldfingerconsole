package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.SplitType
import org.springframework.stereotype.Service
import java.util.*
import kotlin.streams.toList

@Service
class SplitTypeChoiceService: AbstractOrderService<List<SplitType>, String>() {
    override fun fetchOptions(orderBuilder: OrderBuilder): List<SplitType>? {
        return SplitType.values().toList()
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: List<SplitType>) {
        input("${orderBuilder.state.guide} [${options.stream().map { "(${it.alias}) ${it.command}" }.toList().joinToString()}]")
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<String> {
        return ConsoleInput.StringInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: List<SplitType>): Boolean {
        return SplitType.from(input.translation!!) != null
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: List<SplitType>) {
        orderBuilder.splitType = SplitType.from(input.translation!!)
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("${orderBuilder.splitType!!.koText} 골랏읍니다\n")
    }
}