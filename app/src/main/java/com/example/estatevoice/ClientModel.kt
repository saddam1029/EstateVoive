package com.example.estatevoice

import kotlinx.serialization.Serializable

//tells Kotlin how to convert JSON data into a Kotlin object and back.
//Required to read Supabase data
@Serializable
data class ClientModel(
    val id: Int? = null,
    val customer_name: String? = null,
    val call_summary: String? = null,
    val customer_was_satisfied: Boolean = false
)

