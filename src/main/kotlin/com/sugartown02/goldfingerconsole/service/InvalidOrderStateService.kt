package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.EmptyOption
import com.sugartown02.goldfingerconsole.domain.InputValidity
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import org.springframework.stereotype.Service
import java.util.*

@Service
class InvalidOrderStateService: AbstractOrderService<EmptyOption, String>() {
    override fun fetchOptions(orderBuilder: OrderBuilder): EmptyOption? {
        return EmptyOption()
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: EmptyOption) {
        guide("유효하지 않은 상태입니다 : [${orderBuilder.state}]")
        throw RuntimeException("유효하지 않은 상태입니다 : [${orderBuilder.state}]")
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<String> {
        TODO("의도된 노 구현")
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: EmptyOption): InputValidity {
        TODO("의도된 노 구현")
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: EmptyOption) {
        TODO("의도된 노 구현")
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        TODO("의도된 노 구현")
    }
}