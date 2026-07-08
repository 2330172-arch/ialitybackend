package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequest(
    val correo: String
)