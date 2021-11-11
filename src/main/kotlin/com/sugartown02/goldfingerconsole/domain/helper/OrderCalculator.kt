package com.sugartown02.goldfingerconsole.domain.helper

import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import com.sugartown02.goldfingerconsole.domain.SplitType
import java.math.RoundingMode

object OrderCalculator {
    fun findN(money: Int) {
        val totalPyramidElementCount = money / 5000 // = n(n+1)/2
        var n = 0
        for (i in 1..100) {
            if (n + i > totalPyramidElementCount) {
                println(i)
                break
            }
            n += i
            println("${5000 * i} : $i")
        }
        println("total elements: $n")
    }

    fun priceUnit(marketPrice: Double): Double {
        fun range(min: Double, max: Double): Boolean = (min <= marketPrice && marketPrice < max)

        return when {
            range(0.0, 0.1) -> 0.0001
            range(0.1, 1.0) -> 0.001
            range(1.0, 10.0) -> 0.01
            range(10.0, 100.0) -> 0.1
            range(100.0, 1_000.0) -> 1.0
            range(1_000.0, 10_000.0) -> 5.0
            range(10_000.0, 100_000.0) -> 10.0
            range(100_000.0, 500_000.0) -> 50.0
            range(500_000.0, 1_000_000.0) -> 100.0
            range(1_000_000.0, 2_000_000.0) -> 500.0
            else -> 1_000.0
        }
    }
}
