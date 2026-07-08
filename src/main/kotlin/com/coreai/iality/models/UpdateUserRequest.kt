package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val correo: String,
    val nombre: String,
    val password: String,
    val foto: String? = null
)