package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val id: Int,
    val mensaje: String
)