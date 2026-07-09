package com.coreai.iality.routes

import com.coreai.iality.models.Reminder
import com.coreai.iality.repository.ReminderRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reminderRoutes() {

    val repository =
        ReminderRepository()

    route("/recordatorios") {

        get("/{correo}") {

            val correo =
                call.parameters["correo"]

            if (correo == null) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    "Correo requerido"
                )

                return@get
            }

            call.respond(
                repository.obtenerPorCorreo(
                    correo
                )
            )
        }

        post {

            val reminder =
                call.receive<Reminder>()

            val guardado =
                repository.crear(
                    reminder
                )

            call.respond(
                HttpStatusCode.Created,
                guardado
            )
        }

        put("/{id}") {

            val id =
                call.parameters["id"]
                    ?.toIntOrNull()

            if (id == null) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    "ID inválido"
                )

                return@put
            }

            val reminder =
                call.receive<Reminder>()

            val actualizado =
                repository.editar(
                    id,
                    reminder
                )

            if (actualizado) {

                call.respond(
                    HttpStatusCode.OK,
                    reminder.copy(
                        id = id
                    )
                )

            } else {

                call.respond(
                    HttpStatusCode.NotFound,
                    "Recordatorio no encontrado"
                )
            }
        }

        delete("/{id}") {

            val id =
                call.parameters["id"]
                    ?.toIntOrNull()

            if (id == null) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    "ID inválido"
                )

                return@delete
            }

            val eliminado =
                repository.eliminar(id)

            if (eliminado) {

                call.respond(
                    HttpStatusCode.OK,
                    "Recordatorio eliminado"
                )

            } else {

                call.respond(
                    HttpStatusCode.NotFound,
                    "Recordatorio no encontrado"
                )
            }
        }
    }
}