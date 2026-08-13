package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetRequest(
    val correo: String
)

@Serializable
data class PasswordResetConfirmRequest(
    val correo: String,
    val codigo: String,
    val nuevaPassword: String
)

@Serializable
data class PasswordResetResponse(
    val mensaje: String
)