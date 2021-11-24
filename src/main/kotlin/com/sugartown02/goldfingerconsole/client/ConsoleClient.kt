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
class ConsoleClient(
    val orderTypeChoiceService: OrderTypeChoiceService,
    val orderMarketChoiceService: OrderMarketChoiceService,
    val orderCancelMarketChoiceService: OrderCancelMarketChoiceService,
    val orderSideChoiceService: OrderSideChoiceService,
    val splitTypeChoiceService: SplitTypeChoiceService,
    val totalMoneyInputService: TotalMoneyInputService,
    val minPriceInputService: MinPriceInputService,
    val maxPriceInputService: MaxPriceInputService,
    val priceUnitChoiceService: PriceUnitChoiceService,
    val orderConfirmService: OrderConfirmService,
    val orderCancelConfirmService: OrderCancelConfirmService,
    val invalidOrderStateService: InvalidOrderStateService
) {
    companion object {
        val log = logger()
    }

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

        // order specific
        OrderState.CHOOSE_SPLIT_TYPE -> splitTypeChoiceService
        OrderState.INPUT_TOTAL_MONEY -> totalMoneyInputService
        OrderState.INPUT_MIN_PRICE -> minPriceInputService
        OrderState.INPUT_MAX_PRICE -> maxPriceInputService
        OrderState.CHOOSE_PRICE_UNIT -> priceUnitChoiceService

        // confirm
        OrderState.CONFIRM_ORDER -> orderConfirmService
        OrderState.CONFIRM_ORDER_CANCEL -> orderCancelConfirmService

        else -> invalidOrderStateService
    }.execute(orderBuilder, scanner)

    fun run() {
        while (true) {
            try {
                execute()
            } catch (e: Exception) {
                guide("에러낫따: $e")
                guide("문제발생한거 같따. 다시 츄라이 원해요 retry? (Y/n)")
                if (scanner.next().trim().lowercase() == "n") {
                    orderBuilder.state = OrderState.BYE
                }
            }
        }
    }
}
