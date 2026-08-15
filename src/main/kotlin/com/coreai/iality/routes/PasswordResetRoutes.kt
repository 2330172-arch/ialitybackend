package com.coreai.iality.routes

import com.coreai.iality.models.PasswordResetConfirmRequest
import com.coreai.iality.models.PasswordResetRequest
import com.coreai.iality.models.PasswordResetResponse
import com.coreai.iality.service.PasswordResetService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.passwordResetRoutes() {

    val passwordResetService =
        PasswordResetService()

    route("/password-reset") {

        post("/request") {

            val request =
                call.receive<PasswordResetRequest>()

            val correo =
                request.correo.trim().lowercase()

            if (correo.isBlank()) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    PasswordResetResponse(
                        "Ingresa un correo válido."
                    )
                )

                return@post
            }

            try {

                val codigo =
                    passwordResetService.generarCodigo(
                        correo
                    )

                passwordResetService.enviarCorreo(
                    correo = correo,
                    codigo = codigo
                )

                call.respond(
                    HttpStatusCode.OK,
                    PasswordResetResponse(
                        "Código enviado correctamente a tu correo."
                    )
                )

            } catch (e: IllegalArgumentException) {

                call.respond(
                    HttpStatusCode.NotFound,
                    PasswordResetResponse(
                        e.message ?: "Correo no registrado."
                    )
                )

            } catch (e: Exception) {

                println(
                    "ERROR ENVIANDO CORREO: ${e.message}"
                )

                e.printStackTrace()

                call.respond(
                    HttpStatusCode.InternalServerError,
                    PasswordResetResponse(
                        "No fue posible enviar el código."
                    )
                )
            }
        }

        post("/confirm") {

            val request =
                call.receive<PasswordResetConfirmRequest>()

            val correo =
                request.correo.trim().lowercase()

            val codigo =
                request.codigo.trim()

            if (correo.isBlank()) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    PasswordResetResponse(
                        "Correo requerido."
                    )
                )

                return@post
            }

            if (codigo.isBlank()) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    PasswordResetResponse(
                        "Código requerido."
                    )
                )

                return@post
            }

            if (request.nuevaPassword.length < 6) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    PasswordResetResponse(
                        "La contraseña debe tener al menos 6 caracteres."
                    )
                )

                return@post
            }

            val codigoCorrecto =
                passwordResetService.verificarCodigo(
                    correo = correo,
                    codigo = codigo
                )

            if (!codigoCorrecto) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    PasswordResetResponse(
                        "Código incorrecto o expirado."
                    )
                )

                return@post
            }

            val actualizado =
                passwordResetService.cambiarPassword(
                    correo = correo,
                    codigo = codigo,
                    nuevaPassword = request.nuevaPassword
                )

            if (!actualizado) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    PasswordResetResponse(
                        "No fue posible cambiar la contraseña."
                    )
                )

                return@post
            }

            call.respond(
                HttpStatusCode.OK,
                PasswordResetResponse(
                    "Contraseña actualizada correctamente."
                )
            )
        }
    }
}
