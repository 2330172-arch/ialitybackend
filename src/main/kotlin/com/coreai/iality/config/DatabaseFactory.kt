package com.coreai.iality.config

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {

    fun init() {

        val host = System.getenv("PGHOST") ?: "localhost"
        val port = System.getenv("PGPORT") ?: "5432"
        val database = System.getenv("PGDATABASE") ?: "iality"
        val user = System.getenv("PGUSER") ?: "postgres"
        val password = System.getenv("PGPASSWORD") ?: "1234"

        Database.connect(
            url = "jdbc:postgresql://$host:$port/$database",
            driver = "org.postgresql.Driver",
            user = user,
            password = password
        )

        println("===================================")
        println("Base de datos conectada")
        println("Host: $host")
        println("Puerto: $port")
        println("Base: $database")
        println("===================================")
    }
}