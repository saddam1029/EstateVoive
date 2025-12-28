package com.example.estatevoice

import android.content.Context
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
    import io.github.jan.supabase.postgrest.Postgrest

object SupabaseManager {

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
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtmeHd3a3ZkbWltdHV1anphdGNoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjYxMzI4NDcsImV4cCI6MjA4MTcwODg0N30.Wx8khZAp0zDCHRBt1HEZ1pEA82vVoKK5rQkB0yLX-dM"
        ) {

//            Think of Supabase like a mobile phone 📱
//            You install apps/features inside it:
//            GoTrue → Login system
//            Postgrest → Database access

//            install(Auth)

            // Install GoTrue → Supabase Authentication module
            install(GoTrue)

            // Install Postgrest → used for database operations (CRUD)
            // Allows you to read/write data from Supabase tables
            install(Postgrest)
        }
    }

}

