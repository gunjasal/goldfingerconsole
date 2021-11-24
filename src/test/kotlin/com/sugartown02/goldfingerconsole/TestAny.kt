package com.sugartown02.goldfingerconsole

import com.sugartown02.goldfingerconsole.declaration.step
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.math.BigDecimal
import java.util.*


internal class TestAny {
    @Test
    fun `int - double remainder`() {
        assertThat(3.0.rem(21.0)).isEqualTo(3.0)
        assertThat(3.0.rem(2.0)).isEqualTo(1.0)
        assertThat(3.0.rem(3.0)).isEqualTo(0.0)
        assertThat(6.0.rem(3.0)).isEqualTo(0.0)

        assertThat(0.1.rem(3.2)).isEqualTo(0.1)
        assertThat(3.2.rem(0.1)).isEqualTo(0.0)
        assertThat(BigDecimal("3.21").rem(BigDecimal("0.1"))).isEqualTo(BigDecimal("0.01"))
        assertThat(BigDecimal("500000.0").div(BigDecimal("50"))).isEqualTo(BigDecimal("10000.0"))

        println("₩ %,d\n".format(12341243))

        println(1..10)
        println(10 in 1..10)
        println(BigDecimal(3.21.toString()))
        println(BigDecimal(3.21))
        println(3.21)

        val min = 5.1
        val max = 6.3
        val step = 0.1
        for (i in BigDecimal(min.toString()).rangeTo(BigDecimal(max.toString())).step(BigDecimal(step.toString()))) {
            println(i)
        }
    }

    @Test
    fun `print inside map`() {
        val s = "${listOf(11,22,33).mapIndexed { index, i -> "$index $i\n" }}"
        println(s)

        val l = mapOf(1 to 11, 2 to 22, 3 to 33).entries.joinToString(separator = "&") { "${it.key}=${it.value}" }
        println(l)
    }

    @Test
    fun `read properties`() {
        val props = Properties()
        props.load(FileInputStream("./keys.properties"))

        assertThat(props["accessToken"]).isNotNull
        println(props["accessToken"])

        println(3.123.toBigDecimal().toString())
    }
}
