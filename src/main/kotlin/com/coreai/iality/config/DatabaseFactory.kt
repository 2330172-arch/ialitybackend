package com.coreai.iality.config

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {

    fun init() {

        println("===== INIT DATABASE =====")

        Database.connect(
            url = "jdbc:postgresql://localhost:5432/iality",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "1234"
        )

        println("===== DATABASE CONECTADA =====")
    }

}