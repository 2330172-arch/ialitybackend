package com.coreai.iality.routes

import com.coreai.iality.models.ApiResponse
import com.coreai.iality.models.PasswordResetRequest
import com.coreai.iality.models.ResetPasswordRequest
import com.coreai.iality.models.VerifyResetCodeRequest
import com.coreai.iality.repository.PasswordResetRepository
import com.coreai.iality.services.EmailService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlin.random.Random

fun Route.passwordResetRoutes() {

    val repository = PasswordResetRepository()
    val emailService = EmailService()

    post("/password-reset/request") {

        val request = call.receive<PasswordResetRequest>()

        val correo = request.correo.trim().lowercase()

        if (correo.isBlank()) {
            call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse(
                    id = 0,
                    mensaje = "El correo es obligatorio"
                )
            )
            return@post
        }

        val codigo = Random.nextInt(
            from = 100000,
            until = 1000000
        ).toString()

        val expiracion =
            System.currentTimeMillis() + (10 * 60 * 1000)

        try {

            val creado = repository.crearCodigo(
                correo = correo,
                codigo = codigo,
                expiracion = expiracion
            )

            if (!creado) {

                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse(
                        id = 0,
                        mensaje = "Si el correo está registrado, recibirás un código."
                    )
                )

                return@post
            }

            emailService.enviarCodigo(
                correoDestino = correo,
                codigo = codigo
            )

            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    id = 0,
                    mensaje = "Si el correo está registrado, recibirás un código."
                )
            )

        } catch (e: Exception) {

            println("Error enviando código: ${e.message}")

            call.respond(
                HttpStatusCode.InternalServerError,
                ApiResponse(
                    id = 0,
                    mensaje = "No fue posible enviar el código"
                )
            )
        }
    }

    post("/password-reset/verify") {

        val request = call.receive<VerifyResetCodeRequest>()

        val valido = repository.verificarCodigo(
            correo = request.correo.trim().lowercase(),
            codigo = request.codigo.trim()
        )

        if (valido) {

            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    id = 0,
                    mensaje = "Código válido"
                )
            )

        } else {

            call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse(
                    id = 0,
                    mensaje = "Código incorrecto o expirado"
                )
            )
        }
    }

    post("/password-reset/confirm") {

        val request = call.receive<ResetPasswordRequest>()

        if (request.nuevaPassword.length < 6) {

            call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse(
                    id = 0,
                    mensaje = "La contraseña debe tener al menos 6 caracteres"
                )
            )

            return@post
        }

        val cambiado = repository.cambiarPassword(
            correo = request.correo.trim().lowercase(),
            codigo = request.codigo.trim(),
            nuevaPassword = request.nuevaPassword
        )

        if (cambiado) {

            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    id = 0,
                    mensaje = "Contraseña actualizada correctamente"
                )
            )

        } else {

            call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse(
                    id = 0,
                    mensaje = "Código incorrecto, expirado o ya utilizado"
                )
            )
        }
    }
}