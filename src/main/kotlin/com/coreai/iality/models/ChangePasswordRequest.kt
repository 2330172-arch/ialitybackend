package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
    val correo: String,
    val codigo: String,
    val nuevaPassword: String
)