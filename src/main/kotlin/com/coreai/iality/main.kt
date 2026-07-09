package com.coreai.iality

import com.coreai.iality.config.DatabaseFactory
import com.coreai.iality.routes.reminderRoutes
import com.coreai.iality.routes.userRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {

    embeddedServer(

        Netty,

        port = 8080,

        host = "0.0.0.0"

    ) {

        module()

    }.start(

        wait = true
    )
}

fun Application.module() {

    DatabaseFactory.init()

    install(
        ContentNegotiation
    ) {

        json()
    }

    routing {

        userRoutes()

        reminderRoutes()

        get("/") {

            call.respondText(
                "IALITY BACKEND ACTIVO"
            )
        }
    }
}