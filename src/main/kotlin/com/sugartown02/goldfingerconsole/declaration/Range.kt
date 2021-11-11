package com.sugartown02.goldfingerconsole.declaration

import java.math.BigDecimal

infix fun ClosedRange<Double>.step(step: Double): Iterable<Double> {
    require(start.isFinite())
    require(endInclusive.isFinite())
    require(step > 0.0) { "Step must be positive, was: $step." }

    val sequence = generateSequence(start) { previous ->
        if (previous == Double.POSITIVE_INFINITY) return@generateSequence null
        val next = previous + step
        if (next > endInclusive) null else next
    }
    return sequence.asIterable()
}

infix fun ClosedRange<BigDecimal>.step(step: BigDecimal): Iterable<BigDecimal> {
    if (step <= BigDecimal.ZERO) { throw IllegalStateException("Step must be positive, was: $step.") }

    val sequence = generateSequence(start) { previous ->
        if (previous >= BigDecimal(Double.MAX_VALUE)) return@generateSequence null
        val next = previous + step
        if (next > endInclusive) null else next
    }
    return sequence.asIterable()
}
