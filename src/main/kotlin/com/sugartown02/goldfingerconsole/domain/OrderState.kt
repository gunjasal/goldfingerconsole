package com.sugartown02.goldfingerconsole.domain

enum class OrderState(val guide: String)
{
    CHOOSE_TYPE("주문/취소를 선택해주세오?"),
    CHOOSE_ORDER_MARKET("마켓을 선택해주세오?"),
    CHOOSE_ORDER_CANCEL_MARKET("마켓을 선택해주세오?"),
    BUY_OR_SELL("매수/매도를 선택해주세오?"),
    CHOOSE_SPLIT_TYPE("분할방식을 선택해주세오?"),
    INPUT_TOTAL_MONEY("[총 매수/매도 금액]을 숫자만 입력해주세오?"), // money
    INPUT_MIN_PRICE("[가장 낮은 호가]를 입력해주세오?"), // min
    INPUT_MAX_PRICE("[가장 높은 호가]를 입력해주세오?"), // max
    CHOOSE_PRICE_UNIT("분할하고 싶은 호가단위를 입력해주세오?"), // unit
    CONFIRM_ORDER("주문 고고할래오? [Y/n]"),
    CONFIRM_ORDER_CANCEL("주문취소 고고할래오? [Y/n]"),
    BYE("그럼 또 만나요ㅇㅇ");

    val nextOrderState: OrderState
        get() = when (this) {
            CHOOSE_TYPE -> CHOOSE_ORDER_MARKET
            CHOOSE_ORDER_MARKET -> BUY_OR_SELL
            BUY_OR_SELL -> CHOOSE_SPLIT_TYPE
            CHOOSE_SPLIT_TYPE -> INPUT_TOTAL_MONEY
            INPUT_TOTAL_MONEY -> INPUT_MIN_PRICE
            INPUT_MIN_PRICE -> INPUT_MAX_PRICE
            INPUT_MAX_PRICE -> CHOOSE_PRICE_UNIT
            CHOOSE_PRICE_UNIT -> CONFIRM_ORDER
            CONFIRM_ORDER -> CHOOSE_TYPE
            BYE -> BYE
            else -> BYE
        }

    val nextCancelState: OrderState
        get() = when (this) {
            CHOOSE_TYPE -> BUY_OR_SELL
            BUY_OR_SELL -> CHOOSE_ORDER_CANCEL_MARKET
            CHOOSE_ORDER_CANCEL_MARKET -> CONFIRM_ORDER_CANCEL
            CONFIRM_ORDER_CANCEL -> CHOOSE_TYPE
            BYE -> BYE
            else -> BYE
        }
}
