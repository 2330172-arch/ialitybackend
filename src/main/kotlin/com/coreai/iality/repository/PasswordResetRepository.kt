package com.coreai.iality.repository

import com.coreai.iality.database.PasswordResetTable
import com.coreai.iality.database.UsersTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.transactions.transaction

class PasswordResetRepository {

    fun crearCodigo(
        correo: String,
        codigo: String,
        expiracion: Long
    ): Boolean {

        return transaction {

            val usuario = UsersTable
                .selectAll()
                .firstOrNull { fila ->
                    fila[UsersTable.correo] == correo
                }

            if (usuario == null) {
                false
            } else {

                PasswordResetTable
                    .selectAll()
                    .filter { fila ->
                        fila[PasswordResetTable.correo] == correo
                    }
                    .forEach { fila ->

                        val id = fila[PasswordResetTable.id]

                        PasswordResetTable.update(
                            {
                                PasswordResetTable.id eq id
                            }
                        ) {
                            it[PasswordResetTable.usado] = true
                        }
                    }

                PasswordResetTable.insert {

                    it[PasswordResetTable.userId] =
                        usuario[UsersTable.id]

                    it[PasswordResetTable.correo] =
                        correo

                    it[PasswordResetTable.codigo] =
                        codigo

                    it[PasswordResetTable.expiresAt] =
                        expiracion

                    it[PasswordResetTable.usado] =
                        false
                }

                true
            }
        }
    }

    fun verificarCodigo(
        correo: String,
        codigo: String
    ): Boolean {

        val ahora = System.currentTimeMillis()

        return transaction {

            PasswordResetTable
                .selectAll()
                .any { fila ->

                    fila[PasswordResetTable.correo] == correo &&
                            fila[PasswordResetTable.codigo] == codigo &&
                            !fila[PasswordResetTable.usado] &&
                            fila[PasswordResetTable.expiresAt] > ahora
                }
        }
    }

    fun cambiarPassword(
        correo: String,
        codigo: String,
        nuevaPassword: String
    ): Boolean {

        val ahora = System.currentTimeMillis()

        return transaction {

            val codigoValido =
                PasswordResetTable
                    .selectAll()
                    .any { fila ->

                        fila[PasswordResetTable.correo] == correo &&
                                fila[PasswordResetTable.codigo] == codigo &&
                                !fila[PasswordResetTable.usado] &&
                                fila[PasswordResetTable.expiresAt] > ahora
                    }

            if (!codigoValido) {
                false
            } else {

                val actualizado =
                    UsersTable.update(
                        {
                            UsersTable.correo eq correo
                        }
                    ) {
                        it[UsersTable.password] = nuevaPassword
                    } > 0

                if (actualizado) {

                    PasswordResetTable
                        .selectAll()
                        .filter { fila ->
                            fila[PasswordResetTable.correo] == correo &&
                                    fila[PasswordResetTable.codigo] == codigo
                        }
                        .forEach { fila ->

                            val id = fila[PasswordResetTable.id]

                            PasswordResetTable.update(
                                {
                                    PasswordResetTable.id eq id
                                }
                            ) {
                                it[PasswordResetTable.usado] = true
                            }
                        }

                    true

                } else {
                    false
                }
            }
        }
    }
}