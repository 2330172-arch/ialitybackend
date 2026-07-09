package com.coreai.iality.repository

import com.coreai.iality.database.RemindersTable
import com.coreai.iality.models.Reminder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ReminderRepository {

    fun obtenerPorCorreo(
        correo: String
    ): List<Reminder> {

        return transaction {

            RemindersTable
                .selectAll()
                .where {
                    RemindersTable.correoUsuario eq correo
                }
                .map {

                    Reminder(

                        id =
                            it[RemindersTable.id],

                        titulo =
                            it[RemindersTable.titulo],

                        fecha =
                            it[RemindersTable.fecha],

                        hora =
                            it[RemindersTable.hora],

                        correoUsuario =
                            it[RemindersTable.correoUsuario]
                    )
                }
        }
    }

    fun crear(
        reminder: Reminder
    ): Reminder {

        return transaction {

            val idCreado =

                RemindersTable.insert {

                    it[titulo] =
                        reminder.titulo

                    it[fecha] =
                        reminder.fecha

                    it[hora] =
                        reminder.hora

                    it[correoUsuario] =
                        reminder.correoUsuario

                } get RemindersTable.id

            reminder.copy(
                id = idCreado
            )
        }
    }

    fun editar(
        id: Int,
        reminder: Reminder
    ): Boolean {

        return transaction {

            RemindersTable.update(
                {
                    RemindersTable.id eq id
                }
            ) {

                it[titulo] =
                    reminder.titulo

                it[fecha] =
                    reminder.fecha

                it[hora] =
                    reminder.hora

                it[correoUsuario] =
                    reminder.correoUsuario

            } > 0
        }
    }

    fun eliminar(
        id: Int
    ): Boolean {

        return transaction {

            RemindersTable.deleteWhere {

                RemindersTable.id eq id

            } > 0
        }
    }
}