package com.coreai.iality.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val nombre: String,
    val correo: String,
    val password: String,
    val foto: String? = null
)