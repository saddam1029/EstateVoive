package com.example.estatevoice

import kotlinx.serialization.Serializable

@Serializable
data class UserRole(
    val id: String,
    val name: String,
    val role: String,
    val client_id: String? = null,
    val created_at: String? = null
)

