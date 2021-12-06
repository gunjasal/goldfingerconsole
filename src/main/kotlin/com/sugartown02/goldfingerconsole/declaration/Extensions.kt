package com.sugartown02.goldfingerconsole.declaration

import java.math.BigDecimal

fun BigDecimal.isZero() = compareTo(BigDecimal.ZERO) == 0
