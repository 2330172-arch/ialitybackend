package com.coreai.iality.repository

import com.coreai.iality.database.PasswordResetTable
import com.coreai.iality.database.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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
                .where {
                    UsersTable.correo eq correo
                }
                .singleOrNull()

            if (usuario == null) {
                false
            } else {

                PasswordResetTable.update(
                    {
                        PasswordResetTable.correo eq correo
                    }
                ) {
                    it[PasswordResetTable.usado] = true
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
                .where {
                    (PasswordResetTable.correo eq correo) and
                            (PasswordResetTable.codigo eq codigo) and
                            (PasswordResetTable.usado eq false)
                }
                .any {
                    it[PasswordResetTable.expiresAt] > ahora
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
                    .where {
                        (PasswordResetTable.correo eq correo) and
                                (PasswordResetTable.codigo eq codigo) and
                                (PasswordResetTable.usado eq false)
                    }
                    .any {
                        it[PasswordResetTable.expiresAt] > ahora
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
                        it[UsersTable.password] =
                            nuevaPassword
                    } > 0

                if (actualizado) {

                    PasswordResetTable.update(
                        {
                            (PasswordResetTable.correo eq correo) and
                                    (PasswordResetTable.codigo eq codigo)
                        }
                    ) {
                        it[PasswordResetTable.usado] =
                            true
                    }

                    true

                } else {
                    false
                }
            }
        }
    }
}