package com.coreai.iality.routes
import com.coreai.iality.models.ApiResponse
import com.coreai.iality.models.User
import com.coreai.iality.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.coreai.iality.models.LoginRequest
import com.coreai.iality.models.UserProfileRequest
import com.coreai.iality.models.UpdateUserRequest
import io.ktor.server.routing.put

fun Route.userRoutes() {

    val repository = UserRepository()

    post("/registro") {

        val user = call.receive<User>()

        val id = repository.create(user)

        call.respond(
            HttpStatusCode.Created,
            ApiResponse(
                id = id,
                mensaje = "Usuario registrado correctamente"
            )
        )
    }
    post("/login") {

        val request = call.receive<LoginRequest>()

        val user = repository.login(
            request.correo,
            request.password
        )

        if (user == null) {

            call.respond(
                HttpStatusCode.Unauthorized,
                "Correo o contraseña incorrectos"
            )

        } else {

            call.respond(user)

        }
    }

    post("/perfil") {

        val request = call.receive<UserProfileRequest>()

        val user = repository.getByCorreo(request.correo)

        if (user == null) {

            call.respond(
                HttpStatusCode.NotFound,
                "Usuario no encontrado"
            )

        } else {

            call.respond(user)

        }
    }
    put("/perfil") {

        val request = call.receive<UpdateUserRequest>()

        val actualizado = repository.update(
            request.correo,
            request
        )

        if (actualizado) {

            call.respond(HttpStatusCode.OK, "Perfil actualizado")

        } else {

            call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")

        }

    }
    
    post("/sos") {
        try {

            val datosAlerta = call.receive<Map<String, String>>()
            val numeroContacto = datosAlerta["numero"] ?: "Sin número"


            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    id = 0,
                    mensaje = "Alerta de emergencia SOS recibida en el servidor para el contacto: $numeroContacto"
                )
            )
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                "Error al procesar la alerta SOS: ${e.localizedMessage}"
            )
        }
    }
}