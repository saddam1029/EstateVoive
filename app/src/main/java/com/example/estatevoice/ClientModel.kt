package com.example.estatevoice

import kotlinx.serialization.Serializable

@Serializable
data class Call(
    val id: Int? = null,
    val customer_name: String? = null,         // allow nullable
    val call_summary: String? = null,
    val customer_was_satisfied: Boolean = false
)

