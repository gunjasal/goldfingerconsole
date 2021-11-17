package com.sugartown02.goldfingerconsole.client

import com.sugartown02.goldfingerconsole.declaration.logger
import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.OrderState
import com.sugartown02.goldfingerconsole.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PreDestroy

@Service
class ConsoleClient {
    companion object {
        val log = logger()
    }

    @Autowired private lateinit var orderTypeChoiceService: OrderTypeChoiceService
    @Autowired private lateinit var orderMarketChoiceService: OrderMarketChoiceService
    @Autowired private lateinit var orderCancelMarketChoiceService: OrderCancelMarketChoiceService
    @Autowired private lateinit var orderSideChoiceService: OrderSideChoiceService
    @Autowired private lateinit var splitTypeChoiceService: SplitTypeChoiceService
    @Autowired private lateinit var totalMoneyInputService: TotalMoneyInputService
    @Autowired private lateinit var minPriceInputService: MinPriceInputService
    @Autowired private lateinit var maxPriceInputService: MaxPriceInputService
    @Autowired private lateinit var priceUnitChoiceService: PriceUnitChoiceService
    @Autowired private lateinit var orderConfirmService: OrderConfirmService

    private var scanner = Scanner(System.`in`)
    private var orderBuilder = OrderBuilder()

    @PreDestroy
    fun preDestroy() {
        scanner.close()
    }

    private fun execute() = when (orderBuilder.state) {
        OrderState.CHOOSE_TYPE -> orderTypeChoiceService

        OrderState.CHOOSE_ORDER_MARKET -> orderMarketChoiceService
        OrderState.CHOOSE_ORDER_CANCEL_MARKET -> orderCancelMarketChoiceService
        OrderState.BUY_OR_SELL -> orderSideChoiceService

        OrderState.CHOOSE_SPLIT_TYPE -> splitTypeChoiceService
        OrderState.INPUT_TOTAL_MONEY -> totalMoneyInputService
        OrderState.INPUT_MIN_PRICE -> minPriceInputService
        OrderState.INPUT_MAX_PRICE -> maxPriceInputService
        OrderState.CHOOSE_PRICE_UNIT -> priceUnitChoiceService

        OrderState.CONFIRM_ORDER -> orderConfirmService
        OrderState.CONFIRM_ORDER_CANCEL -> orderConfirmService // fixme
        OrderState.COMPLETED -> orderConfirmService // fixme
        OrderState.BYE -> orderConfirmService // fixme
    }.execute(orderBuilder, scanner)

    fun run() {
        while (true) {
            try {
                execute()
            } catch (e: Exception) {
                log.error("에러낫따", e)
            }
        }
    }
}
