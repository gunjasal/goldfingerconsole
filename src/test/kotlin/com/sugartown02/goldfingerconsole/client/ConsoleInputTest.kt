package com.sugartown02.goldfingerconsole.client

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class ConsoleInputTest {
    @Test
    fun `invalid Int input returns null`() {
        val input = ConsoleInput.IntInput("33A")
        Assertions.assertThat(input.translation).isEqualTo(null)
    }

    @Test
    fun `valid Int input returns Int`() {
        val input = ConsoleInput.IntInput("333")
        Assertions.assertThat(input.translation).isEqualTo(333)
    }

    @Test
    fun `valid command is indicated as command + translated as null`() {
        val input = ConsoleInput.IntInput("/bye")
        Assertions.assertThat(input.command).isEqualTo(Command.BYE)
        Assertions.assertThat(input.translation).isEqualTo(null)
    }

    @Test
    fun `valid String input returns String`() {
        val input = ConsoleInput.StringInput("ABC")
        Assertions.assertThat(input.translation).isEqualTo("ABC")
    }
}
