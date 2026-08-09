package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetRequest(
    val correo: String
)

@Serializable
data class VerifyResetCodeRequest(
    val correo: String,
    val codigo: String
)

@Serializable
data class ResetPasswordRequest(
    val correo: String,
    val codigo: String,
    val nuevaPassword: String
)