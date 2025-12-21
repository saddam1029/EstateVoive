package com.example.estatevoice

import android.content.Context
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer

object SupabaseClient {

    // Make sure this client is initialized once in Application class
    // This variable will hold our Supabase client instance
    // 'lateinit' means it will be initialized later (not immediately)
    lateinit var client: SupabaseClient

    // This function initializes Supabase
    // It should be called ONLY ONCE, usually in the Application class
    fun initialize(context: Context) {

        // Create Supabase client using your project URL and public (anon) key
        client = createSupabaseClient(

            // Your Supabase project URL (unique for each project)
            supabaseUrl = "https://kfxwwkvdmimtuujzatch.supabase.co",

            // Public anonymous key (used on client-side apps like Android)
            // ⚠️ This is safe to use in mobile apps
            supabaseKey = "YOUR_ANON_PUBLIC_KEY"
        ) {

            // Install GoTrue → Supabase Authentication module
            install(GoTrue) {

                // Automatically refresh the session when access token expires
                alwaysAutoRefresh = true

                // Automatically save user session (login state)
                // This allows user to stay logged in even after app restart
                autoSaveToStorage = true
            }

            // Install Postgrest → used for database operations (CRUD)
            // Allows you to read/write data from Supabase tables
            install(Postgrest)
        }
    }

}

