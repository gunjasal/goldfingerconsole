package com.sugartown02.goldfingerconsole.declaration

typealias PriceUnit = Double
fun PriceUnit.guide() = "호가단위는 ${"₩ %,.1f 입니다".format(this)}"

