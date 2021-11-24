package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.InputValidity
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
        return upbitApiClient.getOrders(orderBuilder.market!!.code) // todo filter side
    }

    override fun showGuide(orderBuilder: OrderBuilder, options: Orders) {
        options.forEachIndexed { idx, order -> println("($idx) ${order.compact()}") }
        input(orderBuilder.state.guide)
    }

    override fun scanInput(scanner: Scanner): ConsoleInput<String> {
        return ConsoleInput.StringInput(scanner.next())
    }

    override fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: Orders): InputValidity {
        return if (input.translation!!.trim() == "Y") InputValidity.VALID_Y
        else if (input.translation!!.trim() == "n") InputValidity.VALID_N
        else InputValidity.INVALID
    }

    override fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<String>, options: Orders) {
        val uuids = options.map { it.uuid }
        orderBuilder.cancelledOrders = upbitApiClient.cancelOrder(uuids)
    }

    override fun showConfirm(orderBuilder: OrderBuilder) {
        upbitApiClient.getOrders(orderBuilder.market!!.code)
        assure("주문 취소 요청 결과\n${orderBuilder.cancelledOrders?.mapIndexed { idx, cancelledOrder -> "($idx) ${cancelledOrder.info()}\n" }}")
    }
}