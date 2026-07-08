package com.coreai.iality.database

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("usuarios") {

    val id = integer("id").autoIncrement()

    val nombre = varchar("nombre", 100)

    val correo = varchar("correo", 150).uniqueIndex()

    val password = varchar("password", 255)

    val foto = varchar("foto", 255).nullable()

    override val primaryKey = PrimaryKey(id)
}