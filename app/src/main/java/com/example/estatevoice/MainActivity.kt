package com.example.estatevoice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCretate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SupabaseClient.initialize(this)
        fetchClients()
    }

    private fun fetchClients() {
        lifecycleScope.launch {
            try {
                val clients = SupabaseClient.client
                    .postgrest
                    .from("calls")
                    .select() // fetch all rows
                    .decodeList<Call>()

                Log.d("SUPABASE", "Fetched ${clients.size} clients")

                clients.forEach {
                    Log.d("CLIENT_DATA", "Name: ${it.customer_name}, Summary: ${it.call_summary}, Satisfied: ${it.customer_was_satisfied}")
                }

            } catch (e: Exception) {
                Log.e("SUPABASE_ERROR", e.message ?: "Unknown error")
            }
        }

    }

}
