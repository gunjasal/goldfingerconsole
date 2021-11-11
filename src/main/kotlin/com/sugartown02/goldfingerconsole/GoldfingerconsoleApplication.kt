package com.sugartown02.goldfingerconsole

import com.sugartown02.goldfingerconsole.client.ConsoleClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GoldfingerconsoleApplication: CommandLineRunner {
	@Autowired lateinit var consoleClient: ConsoleClient

	override fun run(vararg args: String?) {
		consoleClient.run()
	}
}

fun main(args: Array<String>) {
	runApplication<GoldfingerconsoleApplication>(*args)
}