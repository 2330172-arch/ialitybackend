package com.coreai.iality.repository

import com.coreai.iality.database.UsersTable
import com.coreai.iality.models.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.and
import com.coreai.iality.models.UpdateUserRequest
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserRepository {

    fun create(user: User): Int {

        return transaction {

            UsersTable.insert {

                it[nombre] = user.nombre
                it[correo] = user.correo
                it[password] = user.password
                it[foto] = user.foto

            } get UsersTable.id

        }

    }
    fun update(correo: String, data: UpdateUserRequest): Boolean {

        return transaction {

            UsersTable.update({ UsersTable.correo eq correo }) {

                it[nombre] = data.nombre
                it[password] = data.password
                it[foto] = data.foto

            } > 0

        }

    }

    fun login(correo: String, password: String): User? {

        return transaction {

            UsersTable
                .selectAll()
                .where {
                    (UsersTable.correo eq correo) and
                            (UsersTable.password eq password)
                }
                .map {
                    User(
                        nombre = it[UsersTable.nombre],
                        correo = it[UsersTable.correo],
                        password = it[UsersTable.password],
                        foto = it[UsersTable.foto]
                    )
                }
                .singleOrNull()
        }
    }

    fun getByCorreo(correo: String): User? {

        return transaction {

            UsersTable
                .selectAll()
                .where { UsersTable.correo eq correo }
                .map {
                    User(
                        nombre = it[UsersTable.nombre],
                        correo = it[UsersTable.correo],
                        password = it[UsersTable.password],
                        foto = it[UsersTable.foto]
                    )
                }
                .singleOrNull()
        }
    }

}