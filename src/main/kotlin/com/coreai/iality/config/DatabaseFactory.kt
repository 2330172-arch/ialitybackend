package com.coreai.iality.config

import com.coreai.iality.database.RemindersTable
import com.coreai.iality.database.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {

        val host =
            System.getenv("PGHOST")
                ?: "localhost"

        val port =
            System.getenv("PGPORT")
                ?: "5432"

        val database =
            System.getenv("PGDATABASE")
                ?: "iality"

        val user =
            System.getenv("PGUSER")
                ?: "postgres"

        val password =
            System.getenv("PGPASSWORD")
                ?: "1234"

        Database.connect(

            url =
                "jdbc:postgresql://$host:$port/$database",

            driver =
                "org.postgresql.Driver",

            user = user,

            password = password
        )

        transaction {

            SchemaUtils
                .createMissingTablesAndColumns(

                    UsersTable,

                    RemindersTable
                )
        }

        println(
            "Base de datos conectada"
        )

        println(
            "Tablas usuarios y recordatorios verificadas"
        )
    }
}