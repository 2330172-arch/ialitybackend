package com.coreai.iality.database

import org.jetbrains.exposed.sql.Table

object RemindersTable : Table("recordatorios") {

    val id = integer("id").autoIncrement()

    val titulo = varchar(
        "titulo",
        255
    )

    val fecha = varchar(
        "fecha",
        50
    )

    val hora = varchar(
        "hora",
        50
    )

    val correoUsuario = varchar(
        "correo_usuario",
        255
    )

    override val primaryKey =
        PrimaryKey(id)
}