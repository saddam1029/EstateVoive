package com.example.estatevoice

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.estatevoice.databinding.ActivityCallDetailBinding
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlin.math.log

class CallDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clientId = intent.getStringExtra("clientID")

        if (clientId.isNullOrEmpty()) {
            Log.e("ClientDetail", "Client ID is null")
        }

        getClientDetail(clientId.toString())

    }

    private fun getClientDetail(clientId: String) {
        lifecycleScope.launch {
            try {
                val client: ClientModel = SupabaseManager.client
                    .postgrest
                    .from("clients")
                    .select {
                        eq("id", clientId)
                    }
                    .decodeSingle()

                val name = client.fullName
                Log.e("ClientDetail", name.toString())

            } catch (e: Exception) {
                Log.e("ClientDetail", "Error fetch Client", e)
            }
        }
    }
}