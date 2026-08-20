package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class VerifyPasswordResetRequest(
    val correo: String,
    val codigo: String
)