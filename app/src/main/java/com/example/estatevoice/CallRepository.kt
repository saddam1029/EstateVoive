package com.example.estatevoice

import io.github.jan.supabase.postgrest.postgrest

class CallRepository {

    suspend fun fetchCall(): List<ClientModel> {
        return SupabaseManager.client
            .postgrest
            .from("calls")
            .select()
            .decodeList()
    }
}