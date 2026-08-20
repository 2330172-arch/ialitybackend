package com.coreai.iality.routes

import com.coreai.iality.models.ChangePasswordRequest
import com.coreai.iality.models.PasswordResetRequest
import com.coreai.iality.models.VerifyPasswordResetRequest
import com.coreai.iality.repository.PasswordResetRepository
import com.coreai.iality.repository.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import com.coreai.iality.service.EmailService

fun Route.passwordResetRoutes() {

    val passwordResetRepository =
        PasswordResetRepository()

    val userRepository =
        UserRepository()
    val emailService = EmailService()


    // =========================================
    // SOLICITAR CÓDIGO
    // =========================================

    post("/password/solicitar") {

        val request =
            call.receive<PasswordResetRequest>()

        val usuario =
            userRepository.getByCorreo(
                request.correo
            )

        if (usuario == null) {

            call.respond(
                HttpStatusCode.NotFound,
                "No existe una cuenta con ese correo"
            )

            return@post
        }

        val codigo =
            passwordResetRepository.generarCodigo(
                request.correo
            )

        emailService.enviarCodigo(
            request.correo,
            codigo
        )

        call.respond(
            HttpStatusCode.OK,
            "Código enviado al correo"
        )
    }


    // =========================================
    // VERIFICAR CÓDIGO
    // =========================================

    post("/password/verificar") {

        val request =
            call.receive<VerifyPasswordResetRequest>()

        val valido =
            passwordResetRepository.validarCodigo(
                request.correo,
                request.codigo
            )

        if (valido) {

            call.respond(
                HttpStatusCode.OK,
                "Código correcto"
            )

        } else {

            call.respond(
                HttpStatusCode.BadRequest,
                "Código incorrecto o expirado"
            )
        }
    }


    // =========================================
    // CAMBIAR CONTRASEÑA
    // =========================================

    post("/password/cambiar") {

        val request =
            call.receive<ChangePasswordRequest>()

        val codigoValido =
            passwordResetRepository.validarCodigo(
                request.correo,
                request.codigo
            )

        if (!codigoValido) {

            call.respond(
                HttpStatusCode.BadRequest,
                "Código incorrecto o expirado"
            )

            return@post
        }

        val actualizado =
            userRepository.cambiarPassword(
                request.correo,
                request.nuevaPassword
            )

        if (!actualizado) {

            call.respond(
                HttpStatusCode.NotFound,
                "Usuario no encontrado"
            )

            return@post
        }

        passwordResetRepository.marcarCodigoUsado(
            request.correo,
            request.codigo
        )

        call.respond(
            HttpStatusCode.OK,
            "Contraseña actualizada correctamente"
        )
    }
}