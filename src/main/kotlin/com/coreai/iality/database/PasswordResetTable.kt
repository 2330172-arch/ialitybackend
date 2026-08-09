package com.coreai.iality.database

import org.jetbrains.exposed.sql.Table

object PasswordResetTable : Table("password_reset_codes") {

    val id = integer("id").autoIncrement()

    val userId = integer("user_id")
        .references(UsersTable.id)

    val correo = varchar("correo", 150)

    val codigo = varchar("codigo", 6)

    val expiresAt = long("expires_at")

    val usado = bool("usado")
        .default(false)

    override val primaryKey = PrimaryKey(id)
}