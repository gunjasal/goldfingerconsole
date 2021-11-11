package com.sugartown02.goldfingerconsole.domain.helper

import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.OrderSide
import com.sugartown02.goldfingerconsole.domain.SplitType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class OrderCalculatorTest {
    @Test
    fun `find n`() {
        OrderCalculator.findN(500_000)
    }

    @Test
    fun `price unit`() {
        assertThat(OrderCalculator.priceUnit(2_000_000.0)).isEqualTo(1000.0)
        assertThat(OrderCalculator.priceUnit(2_000_001.0)).isEqualTo(1000.0)

        assertThat(OrderCalculator.priceUnit(1_000_000.0)).isEqualTo(500.0)
        assertThat(OrderCalculator.priceUnit(1_999_999.0)).isEqualTo(500.0)

        assertThat(OrderCalculator.priceUnit(500_000.0)).isEqualTo(100.0)
        assertThat(OrderCalculator.priceUnit(999_999.0)).isEqualTo(100.0)

        assertThat(OrderCalculator.priceUnit(100_000.0)).isEqualTo(50.0)
        assertThat(OrderCalculator.priceUnit(499_999.0)).isEqualTo(50.0)

        assertThat(OrderCalculator.priceUnit(10_000.0)).isEqualTo(10.0)
        assertThat(OrderCalculator.priceUnit(99_999.0)).isEqualTo(10.0)

        assertThat(OrderCalculator.priceUnit(1_000.0)).isEqualTo(5.0)
        assertThat(OrderCalculator.priceUnit(9_999.0)).isEqualTo(5.0)

        assertThat(OrderCalculator.priceUnit(100.0)).isEqualTo(1.0)
        assertThat(OrderCalculator.priceUnit(999.0)).isEqualTo(1.0)

        assertThat(OrderCalculator.priceUnit(10.0)).isEqualTo(0.1)
        assertThat(OrderCalculator.priceUnit(99.0)).isEqualTo(0.1)

        assertThat(OrderCalculator.priceUnit(1.0)).isEqualTo(0.01)
        assertThat(OrderCalculator.priceUnit(9.0)).isEqualTo(0.01)

        assertThat(OrderCalculator.priceUnit(0.1)).isEqualTo(0.001)
        assertThat(OrderCalculator.priceUnit(0.9)).isEqualTo(0.001)

        assertThat(OrderCalculator.priceUnit(0.01)).isEqualTo(0.0001)
        assertThat(OrderCalculator.priceUnit(0.09)).isEqualTo(0.0001)


        //    2,000,000원 이상	1,000원
//    1,000,000원 이상~2,000,000원 미만	500원
//    500,000원 이상~1,000,000원 미만	100원
//    100,000원 이상~500,000원 미만	50원
//    10,000원 이상~100,000원 미만	10원
//    1,000원 이상~10,000원 미만	5원
//    1,000원 미만	1원
//    100원 미만	0.1원
//    10원 미만	0.01원
//    1원 미만	0.001원
//    0.1원 미만	0.0001원
    }
}
