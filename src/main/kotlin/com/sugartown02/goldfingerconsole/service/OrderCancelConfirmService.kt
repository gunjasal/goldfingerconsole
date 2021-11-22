package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.model.Orders
import com.sugartown02.goldfingerconsole.http.UpbitApiClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderCancelConfirmService(
    val upbitApiClient: UpbitApiClient
): AbstractOrderService<Orders, String>() {

    override fun fetchOptions(orderBuilder: OrderBuilder): Orders? {
        return upbitApiClient.orders(orderBuilder.market!!.code) // todo side
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: Orders) {
        options.forEachIndexed { idx, order -> println("($idx) ${order.compact()}") }
        input(orderBuilder.state.guide)
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<String> {
        return ConsoleInput.StringInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: Orders): Boolean {
        return input.translation!!.trim() == "Y"
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: Orders) {
        val uuids = options.map { it.uuid }
        orderBuilder.cancelledOrders = upbitApiClient.cancelOrder(uuids)
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        upbitApiClient.orders(orderBuilder.market!!.code)
        assure("${orderBuilder.cancelledOrders?.mapIndexed { idx, cancelledOrder -> "($idx) ${cancelledOrder.compact()}\n" }}")
    }
}