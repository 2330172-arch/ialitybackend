package com.coreai.iality.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val id: Int,
    val mensaje: String
)