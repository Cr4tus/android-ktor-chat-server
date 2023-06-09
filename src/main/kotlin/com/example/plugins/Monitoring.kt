package com.example.plugins

import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.application.*
import io.ktor.request.*

fun Application.configureMonitoring() {
    install(CallLogging) {
        this.level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}
