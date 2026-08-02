package com.coreai.iality.database

import org.jetbrains.exposed.sql.Table

object EmergencyContactsTable : Table("emergency_contacts") {

    val id = integer("id").autoIncrement()

    val userId = integer("user_id")
        .references(UsersTable.id)

    val nombre = varchar("nombre", 100)

    val telefono = varchar("telefono", 20)

    override val primaryKey = PrimaryKey(id)
}