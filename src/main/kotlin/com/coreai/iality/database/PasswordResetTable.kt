package com.coreai.iality.database

import org.jetbrains.exposed.sql.Table

object PasswordResetTable : Table("password_reset") {

    val id = integer("id").autoIncrement()

    val correo = varchar("correo", 150)

    val codigo = varchar("codigo", 6)

    val expiraEn = long("expira_en")

    val usado = bool("usado").default(false)

    override val primaryKey = PrimaryKey(id)
}