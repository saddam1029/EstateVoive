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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchClients()

    }

    // This function fetches client(call) data from Supabase
    private fun fetchClients() {

        // lifecycleScope is a coroutine scope tied to Activity/Fragment lifecycle
        // launch {} runs this code in background (not on main UI thread)
        lifecycleScope.launch {
            try {

                // Access Supabase client
                val clients = SupabaseClient.client

                    // Access PostgREST (database) module
                    .postgrest

                    // Select the "calls" table from Supabase database
                    .from("calls")

                    // Fetch all rows from the table
                    // (same as SELECT * FROM calls)
                    .select()

                    // Convert (decode) the JSON response into a List<Call>
                    // Each row becomes a Call object
                    .decodeList<Call>()

                // Log how many records were fetched
                Log.d("SUPABASE", "Fetched ${clients.size} clients")

                // Loop through each Call object
                clients.forEach {

                    // Log individual fields from each call
                    Log.d(
                        "CLIENT_DATA",
                        "Name: ${it.customer_name}, " +
                                "Summary: ${it.call_summary}, " +
                                "Satisfied: ${it.customer_was_satisfied}"
                    )
                }

            } catch (e: Exception) {
                // If any error occurs (network, decoding, table issue, etc.)
                Log.e("SUPABASE_ERROR", e.message ?: "Unknown error")
            }
        }
    }

}
