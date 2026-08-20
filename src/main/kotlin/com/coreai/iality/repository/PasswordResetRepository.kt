package com.coreai.iality.repository

import com.coreai.iality.database.PasswordResetTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class PasswordResetRepository {

    // =========================================
    // GENERAR CÓDIGO
    // =========================================

    fun generarCodigo(correo: String): String {

        val codigo =
            (100000..999999)
                .random()
                .toString()

        val expiraEn =
            System.currentTimeMillis() +
                    (10 * 60 * 1000)

        transaction {

            // Invalidar códigos anteriores
            PasswordResetTable.update(
                {
                    PasswordResetTable.correo eq correo
                }
            ) {

                it[usado] = true
            }

            // Guardar nuevo código
            PasswordResetTable.insert {

                it[PasswordResetTable.correo] =
                    correo

                it[PasswordResetTable.codigo] =
                    codigo

                it[PasswordResetTable.expiraEn] =
                    expiraEn

                it[PasswordResetTable.usado] =
                    false
            }
        }

        return codigo
    }


    // =========================================
    // VALIDAR CÓDIGO
    // =========================================

    fun validarCodigo(
        correo: String,
        codigo: String
    ): Boolean {

        val ahora =
            System.currentTimeMillis()

        return transaction {

            PasswordResetTable
                .selectAll()
                .where {
                    PasswordResetTable.correo eq correo
                }
                .any {

                    it[PasswordResetTable.codigo] == codigo &&
                            !it[PasswordResetTable.usado] &&
                            it[PasswordResetTable.expiraEn] > ahora
                }
        }
    }


    // =========================================
    // MARCAR CÓDIGO COMO USADO
    // =========================================

    fun marcarCodigoUsado(
        correo: String,
        codigo: String
    ) {

        transaction {

            val registro =
                PasswordResetTable
                    .selectAll()
                    .where {
                        PasswordResetTable.correo eq correo
                    }
                    .firstOrNull {
                        it[PasswordResetTable.codigo] == codigo &&
                                !it[PasswordResetTable.usado]
                    }

            if (registro != null) {

                val id =
                    registro[PasswordResetTable.id]

                PasswordResetTable.update(
                    {
                        PasswordResetTable.id eq id
                    }
                ) {

                    it[PasswordResetTable.usado] = true
                }
            }
        }
    }
}