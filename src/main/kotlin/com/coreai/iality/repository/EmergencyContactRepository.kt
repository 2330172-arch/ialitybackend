package com.coreai.iality.repository

import com.coreai.iality.database.EmergencyContactsTable
import com.coreai.iality.models.EmergencyContact
import com.coreai.iality.models.EmergencyContactRequest
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class EmergencyContactRepository {

    fun create(contact: EmergencyContactRequest): Int {

        return transaction {

            EmergencyContactsTable.insert {

                it[userId] = contact.userId
                it[nombre] = contact.nombre
                it[telefono] = contact.telefono

            } get EmergencyContactsTable.id
        }
    }

    fun getByUserId(userId: Int): EmergencyContact? {

        return transaction {

            EmergencyContactsTable
                .selectAll()
                .where {
                    EmergencyContactsTable.userId eq userId
                }
                .map {

                    EmergencyContact(
                        id = it[EmergencyContactsTable.id],
                        userId = it[EmergencyContactsTable.userId],
                        nombre = it[EmergencyContactsTable.nombre],
                        telefono = it[EmergencyContactsTable.telefono]
                    )
                }
                .singleOrNull()
        }
    }

    fun deleteByUserId(userId: Int): Boolean {

        return transaction {

            EmergencyContactsTable.deleteWhere {
                EmergencyContactsTable.userId eq userId
            } > 0
        }
    }
}