package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class Reminder(

    val id: Int? = null,

    val titulo: String,

    val fecha: String,

    val hora: String,

    val correoUsuario: String
)