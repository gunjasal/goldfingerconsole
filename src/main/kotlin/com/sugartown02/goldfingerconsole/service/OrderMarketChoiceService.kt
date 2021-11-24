package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.InputValidity
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.model.Markets
import com.sugartown02.goldfingerconsole.domain.model.list
import com.sugartown02.goldfingerconsole.domain.model.print
import com.sugartown02.goldfingerconsole.http.UpbitApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderMarketChoiceService: AbstractOrderService<Markets, Int>() {
    @Autowired lateinit var upbitApiClient: UpbitApiClient

    override fun fetchOptions(orderBuilder: OrderBuilder): Markets? {
        return upbitApiClient.marketAll()
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: Markets) {
        options.print(true)
        input(orderBuilder.state.guide)
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<Int> {
        return ConsoleInput.IntInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<Int>, options: Markets): InputValidity {
        return if (options.list().getOrNull(input.translation!!) != null) InputValidity.VALID_Y else InputValidity.INVALID
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<Int>, options: Markets) {
        orderBuilder.market = options.list()[input.translation!!]
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("${orderBuilder.market!!.info()} 골랏읍니다\n")
    }
}
