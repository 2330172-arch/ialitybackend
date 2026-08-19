package com.coreai.iality.repository

import com.coreai.iality.database.UsersTable
import com.coreai.iality.models.User
import com.coreai.iality.models.UpdateUserRequest
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserRepository {

    // =========================================
    // CREAR USUARIO
    // =========================================

    fun create(user: User): Int {

        return transaction {

            UsersTable.insert {

                it[nombre] = user.nombre
                it[correo] = user.correo
                it[password] = user.password
                it[foto] = user.foto
                it[palabraClave] = user.palabraClave

            } get UsersTable.id
        }
    }

    // =========================================
    // ACTUALIZAR USUARIO
    // =========================================

    fun update(
        correoActual: String,
        data: UpdateUserRequest
    ): Boolean {

        return transaction {

            // Verificar si el nuevo correo
            // pertenece a otro usuario
            val correoExiste =
                UsersTable
                    .selectAll()
                    .where {
                        UsersTable.correo eq data.nuevoCorreo
                    }
                    .any {
                        it[UsersTable.correo] != correoActual
                    }

            if (correoExiste) {
                return@transaction false
            }

            // Actualizar usuario
            UsersTable.update(
                { UsersTable.correo eq correoActual }
            ) {

                it[nombre] = data.nombre
                it[correo] = data.nuevoCorreo
                it[password] = data.password
                it[foto] = data.foto
                it[palabraClave] = data.palabraClave

            } > 0
        }
    }

    // =========================================
    // LOGIN
    // =========================================

    fun login(
        correo: String,
        password: String
    ): User? {

        return transaction {

            UsersTable
                .selectAll()
                .where {
                    (UsersTable.correo eq correo) and
                            (UsersTable.password eq password)
                }
                .map {

                    User(
                        id = it[UsersTable.id],
                        nombre = it[UsersTable.nombre],
                        correo = it[UsersTable.correo],
                        password = it[UsersTable.password],
                        foto = it[UsersTable.foto],
                        palabraClave = it[UsersTable.palabraClave]
                    )
                }
                .singleOrNull()
        }
    }

    // =========================================
    // OBTENER PERFIL
    // =========================================

    fun getByCorreo(
        correo: String
    ): User? {

        return transaction {

            UsersTable
                .selectAll()
                .where {
                    UsersTable.correo eq correo
                }
                .map {

                    User(
                        id = it[UsersTable.id],
                        nombre = it[UsersTable.nombre],
                        correo = it[UsersTable.correo],
                        password = it[UsersTable.password],
                        foto = it[UsersTable.foto],
                        palabraClave = it[UsersTable.palabraClave]
                    )
                }
                .singleOrNull()
        }
    }
}