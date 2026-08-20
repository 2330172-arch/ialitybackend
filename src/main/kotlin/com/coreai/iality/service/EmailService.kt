package com.coreai.iality.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

@Serializable
data class ResendEmailRequest(
    val from: String,
    val to: List<String>,
    val subject: String,
    val html: String
)

class EmailService {

    private val apiKey =
        System.getenv("RESEND_API_KEY")

    private val client =
        HttpClient(CIO) {

            install(ContentNegotiation) {
                json()
            }
        }

    suspend fun enviarCodigo(
        correo: String,
        codigo: String
    ) {

        require(!apiKey.isNullOrBlank()) {
            "RESEND_API_KEY no está configurada"
        }

        client.post("https://api.resend.com/emails") {

            contentType(ContentType.Application.Json)

            header(
                HttpHeaders.Authorization,
                "Bearer $apiKey"
            )

            setBody(
                ResendEmailRequest(
                    from = "IALITY <onboarding@resend.dev>",
                    to = listOf(correo),
                    subject = "Código para recuperar tu contraseña",
                    html = """
                        <h2>Recuperación de contraseña</h2>

                        <p>Hola,</p>

                        <p>
                            Recibimos una solicitud para recuperar
                            tu contraseña de IALITY.
                        </p>

                        <h1>$codigo</h1>

                        <p>
                            Este código es temporal.
                        </p>

                        <p>
                            Si tú no solicitaste este cambio,
                            puedes ignorar este correo.
                        </p>

                        <br>

                        <p>
                            <strong>CORE IA</strong>
                        </p>
                    """.trimIndent()
                )
            )
        }
    }
}