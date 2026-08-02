package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val password: String,
    val foto: String? = null
)