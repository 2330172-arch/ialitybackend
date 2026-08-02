package com.coreai.iality.models

import kotlinx.serialization.Serializable

@Serializable
data class EmergencyContactRequest(
    val userId: Int,
    val nombre: String,
    val telefono: String
)

@Serializable
data class EmergencyContact(
    val id: Int,
    val userId: Int,
    val nombre: String,
    val telefono: String
)