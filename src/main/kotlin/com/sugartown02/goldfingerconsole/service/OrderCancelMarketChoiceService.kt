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
class OrderCancelMarketChoiceService: AbstractOrderService<Markets, Int>() {
    @Autowired
    lateinit var upbitApiClient: UpbitApiClient

    override fun fetchOptions(orderBuilder: OrderBuilder): Markets? {
        val markets = upbitApiClient.marketAll()
        val orders = upbitApiClient.getOrders()
        if (!orders.isNullOrEmpty()) {
            val orderMarkets = orders.distinctBy { it.market }.map { it.market }.toSet() // todo filter side
            return markets!!.filter { orderMarkets.contains(it.code.id) }
        }

        return emptyList()
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: Markets) {
        if (!options.isNullOrEmpty()) {
            options.print()
            input(orderBuilder.state.guide)
        } else {
            throw IllegalStateException("주문내역이 없습닏.")
        }

    }

    override fun scanInput(scanner: Scanner): ConsoleInput<Int> {
        return ConsoleInput.IntInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<Int>, options: Markets): InputValidity {
        return if (options.list().getOrNull(input.translation!!) != null) InputValidity.VALID_Y
        else InputValidity.INVALID
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<Int>, options: Markets) {
        orderBuilder.market = options.list()[input.translation!!]
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        assure("${orderBuilder.market!!.info()} 골랏읍니다\n")
    }
}
