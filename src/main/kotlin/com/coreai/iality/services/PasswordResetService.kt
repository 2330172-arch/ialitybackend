package com.coreai.iality.service

import com.coreai.iality.repository.PasswordResetRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlin.random.Random
import kotlinx.serialization.json.Json

class PasswordResetService(
    private val repository: PasswordResetRepository = PasswordResetRepository()
) {

    private val resendApiKey = System.getenv("RESEND_API_KEY")
        ?: throw IllegalStateException("RESEND_API_KEY no configurado en Railway")

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun generarCodigo(correo: String): String {

        val codigo = Random.nextInt(100000, 1000000).toString()

        val expiracion =
            System.currentTimeMillis() + (10 * 60 * 1000)

        val creado =
            repository.crearCodigo(
                correo = correo,
                codigo = codigo,
                expiracion = expiracion
            )

        if (!creado) {
            throw IllegalArgumentException(
                "El correo no está registrado."
            )
        }

        println("[IALITY] Código generado: $codigo para $correo")

        return codigo
    }

    fun verificarCodigo(
        correo: String,
        codigo: String
    ): Boolean {

        return repository.verificarCodigo(
            correo = correo,
            codigo = codigo
        )
    }

    fun cambiarPassword(
        correo: String,
        codigo: String,
        nuevaPassword: String
    ): Boolean {

        return repository.cambiarPassword(
            correo = correo,
            codigo = codigo,
            nuevaPassword = nuevaPassword
        )
    }

    suspend fun enviarCorreo(
        correo: String,
        codigo: String
    ) {

        println("[RESEND] Enviando código a: $correo")

        try {

            val requestBody = ResendEmailRequest(
                from = "onboarding@resend.dev",
                to = correo,
                subject = "Código de recuperación - IALITY",
                html = """
                    <html>
                    <body style="font-family: Arial, sans-serif; padding: 20px;">
                        <h2>Recuperación de contraseña - IALITY</h2>
                        <p>Hola,</p>
                        <p>Tu código para recuperar tu contraseña de IALITY es:</p>
                        <h1 style="color: #007AFF; font-size: 36px; letter-spacing: 5px;">$codigo</h1>
                        <p>Este código tiene una duración de 10 minutos.</p>
                        <p>Si tú no solicitaste este cambio, ignora este correo.</p>
                        <p>IALITY</p>
                    </body>
                    </html>
                """.trimIndent()
            )

            val response = httpClient.post("https://api.resend.com/emails") {
                header("Authorization", "Bearer $resendApiKey")
                header("Content-Type", "application/json")
                setBody(requestBody)
            }

            println("[RESEND] ✅ Correo enviado correctamente a $correo")

        } catch (e: Exception) {

            println("[RESEND] ❌ ERROR: ${e.message}")

            e.printStackTrace()

            throw RuntimeException(
                "Error enviando correo con Resend: ${e.message}",
                e
            )
        }
    }
}

@Serializable
data class ResendEmailRequest(
    val from: String,
    val to: String,
    val subject: String,
    val html: String
)