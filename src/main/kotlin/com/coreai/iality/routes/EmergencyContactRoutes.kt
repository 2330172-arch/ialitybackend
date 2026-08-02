package com.coreai.iality.routes

import com.coreai.iality.models.ApiResponse
import com.coreai.iality.models.EmergencyContactRequest
import com.coreai.iality.repository.EmergencyContactRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.emergencyContactRoutes() {

    val repository = EmergencyContactRepository()

    // Guardar contacto de emergencia
    post("/emergency-contact") {

        val request = call.receive<EmergencyContactRequest>()

        val id = repository.create(request)

        call.respond(
            HttpStatusCode.Created,
            ApiResponse(
                id = id,
                mensaje = "Contacto de emergencia guardado correctamente"
            )
        )
    }

    // Obtener contacto de emergencia de un usuario
    get("/emergency-contact/{userId}") {

        val userId = call.parameters["userId"]?.toIntOrNull()

        if (userId == null) {

            call.respond(
                HttpStatusCode.BadRequest,
                "ID de usuario inválido"
            )

            return@get
        }

        val contact = repository.getByUserId(userId)

        if (contact == null) {

            call.respond(
                HttpStatusCode.NotFound,
                "Contacto de emergencia no encontrado"
            )

        } else {

            call.respond(contact)
        }
    }

    // Eliminar contacto
    delete("/emergency-contact/{userId}") {

        val userId = call.parameters["userId"]?.toIntOrNull()

        if (userId == null) {

            call.respond(
                HttpStatusCode.BadRequest,
                "ID de usuario inválido"
            )

            return@delete
        }

        val eliminado = repository.deleteByUserId(userId)

        if (eliminado) {

            call.respond(
                HttpStatusCode.OK,
                "Contacto eliminado"
            )

        } else {

            call.respond(
                HttpStatusCode.NotFound,
                "Contacto de emergencia no encontrado"
            )
        }
    }
}