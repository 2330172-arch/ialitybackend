package com.coreai.iality

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ServerTest {

    @Test
    fun testRoot() = testApplication {

        application {
            routing {
                get("/") {
                    call.respondText("IALITY BACKEND ACTIVO")
                }
            }
        }

        val response = client.get("/")

        assertEquals(
            "IALITY BACKEND ACTIVO",
            response.bodyAsText()
        )
    }
}