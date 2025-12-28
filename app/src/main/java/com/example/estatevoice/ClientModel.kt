package com.example.estatevoice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//tells Kotlin how to convert JSON data into a Kotlin object and back.
//Required to read Supabase data
@Serializable
data class ClientModel(
    val id: String? = null,

    @SerialName("full_name")
    val fullName: String? = null,

    @SerialName("business_name")
    val businessName: String? = null,

    val phone: String,

    val email: String? = null,
    val role: String? = null,
    val city: String? = null,

    @SerialName("intent_level")
    val intentLevel: String? = null,

    @SerialName("follow_up_required")
    val followUpRequired: Boolean = false,

    @SerialName("follow_up_reason")
    val followUpReason: String? = null,

    @SerialName("recording_url")
    val recordingUrl: String? = null,

    @SerialName("call_summary")
    val callSummary: String? = null,

    @SerialName("internal_notes")
    val internalNotes: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null
)


