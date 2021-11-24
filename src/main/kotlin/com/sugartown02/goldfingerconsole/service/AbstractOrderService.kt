package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.client.Command
import com.sugartown02.goldfingerconsole.client.ConsoleInput
import com.sugartown02.goldfingerconsole.domain.InputValidity
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.OrderState
import com.sugartown02.goldfingerconsole.domain.OrderType
import java.util.*
import kotlin.system.exitProcess

abstract class AbstractOrderService<O, I>: Orderable {
    override fun execute(orderBuilder: OrderBuilder, scanner: Scanner) {
        if (orderBuilder.state == OrderState.BYE) {
            bye()
        }

        fetchOptions(orderBuilder)?.let { options ->
            showGuide(orderBuilder, options)

            val input = scanInput(scanner)
            input.command?.let { cmd ->
                processCommand(cmd, orderBuilder)
            } ?: run {
                input.translation?.let {
                    when (valid(orderBuilder, input, options)) {
                        InputValidity.VALID_Y -> {
                            updateOrder(orderBuilder, input, options)
                            orderBuilder.state =
                                if (orderBuilder.type == OrderType.ORDER) orderBuilder.state.nextOrderState
                                else orderBuilder.state.nextCancelState
                            showConfirm(orderBuilder)
                        }
                        InputValidity.VALID_N -> {
                            guide("주문을 초기화할게오.")
                            orderBuilder.init()
                        }
                        InputValidity.INVALID -> guide("잘못입력햇아오. 다시 입력해주세오.")
                    }
                } ?: run {
                    guide("잘못입력햇아오. 다시 입력해주세오.")
                }
            }
        }
    }

    abstract fun fetchOptions(orderBuilder: OrderBuilder): O?
    abstract fun showGuide(orderBuilder: OrderBuilder, options: O)
    abstract fun scanInput(scanner: Scanner): ConsoleInput<I>
    abstract fun valid(orderBuilder: OrderBuilder, input: ConsoleInput<I>, options: O): InputValidity
    abstract fun updateOrder(orderBuilder: OrderBuilder, input: ConsoleInput<I>, options: O)
    abstract fun showConfirm(orderBuilder: OrderBuilder)

    private fun processCommand(
        cmd: Command,
        orderBuilder: OrderBuilder,
    ) {
        when (cmd) {
            Command.BYE -> bye()
            Command.INIT -> orderBuilder.init()
            Command.MONEY -> orderBuilder.reprice() // todo remove or check cmd order
        }

        cmd.nextState?.let {
            orderBuilder.state = cmd.nextState
        }
    }

    private fun bye() {
        guide("bye!")
        exitProcess(1)
    }

    private fun isYn(input: String) = input in setOf("Y", "n")
}

fun input(message: String) {
    print("➜  $message  ")
}

fun guide(message: String) {
    println("✗  $message\n")
}

fun assure(message: String) {
    println("★  $message")
}
