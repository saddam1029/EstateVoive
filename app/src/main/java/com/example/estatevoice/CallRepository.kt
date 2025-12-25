package com.example.estatevoice

import android.util.Log
import io.github.jan.supabase.postgrest.postgrest

class CallRepository {

    suspend fun fetchCall(): List<ClientModel> {
        return try {
            SupabaseManager.client
                .postgrest
                .from("clients")
                .select()
                .decodeList<ClientModel>()
        } catch (e: Exception) {
            Log.e("CallDebug", "Supabase fetch failed", e)
            emptyList()
        }
    }
}
