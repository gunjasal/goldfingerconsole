package com.sugartown02.goldfingerconsole.service

import com.sugartown02.goldfingerconsole.domain.OrderBuilder
import java.util.*

interface Orderable {
    fun execute(orderBuilder: OrderBuilder, scanner: Scanner)
}