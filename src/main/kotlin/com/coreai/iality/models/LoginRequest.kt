package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val correo: String,
    val password: String
)