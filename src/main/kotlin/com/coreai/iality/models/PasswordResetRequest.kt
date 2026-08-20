package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetRequest(
    val correo: String
)