package com.sugartown02.goldfingerconsole.http.helper

import com.sugartown02.goldfingerconsole.declaration.logger
import java.io.FileInputStream
import java.util.*

object PropertyReader {
    private val log = logger()

    fun keyProperties(): Properties {
        val props = Properties()
        try {
            FileInputStream("./keys.properties").use {
                props.load(it)
            }
        } catch (e: Exception) {
            log.error("keys.properties error: $e")
            throw e
        }

        if (props["accessToken"] == null || props["accessToken"].toString().isBlank()) throw IllegalStateException("no accessToken in keys.properties")
        if (props["secretToken"] == null || props["secretToken"].toString().isBlank()) throw IllegalStateException("no secretToken in keys.properties")

        return props
    }
}