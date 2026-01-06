package com.example.estatevoice

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.estatevoice.databinding.ActivityCreateClientBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import kotlinx.coroutines.withContext


class CreateClientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateClientBinding

    private val supabaseAnonKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtmeHd3a3ZkbWltdHV1anphdGNoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjYxMzI4NDcsImV4cCI6MjA4MTcwODg0N30.Wx8khZAp0zDCHRBt1HEZ1pEA82vVoKK5rQkB0yLX-dM"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            accountCreation()
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun accountCreation() {
        val email = binding.etEmail.text.toString().trim().lowercase()
        val password = binding.etPassword.text.toString().trim()
        val conPassword = binding.etConfirmPassword.text.toString().trim()
        val name = binding.etClientName.text.toString().trim() // Add name field in layout

        if (email.isEmpty() || password.isEmpty() || conPassword.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != conPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                // Run network code in IO dispatcher
                val response = withContext(Dispatchers.IO) {
                    val jsonBody = """
                    {
                        "email": "$email",
                        "password": "$password",
                        "name": "$name"
                    }
                """.trimIndent()

                    val client = okhttp3.OkHttpClient()
                    val requestBody = okhttp3.RequestBody.create(
                        "application/json".toMediaTypeOrNull(),
                        jsonBody
                    )

                    val request = okhttp3.Request.Builder()
                        .url("https://kfxwwkvdmimtuujzatch.supabase.co/functions/v1/swift-function")
                        .post(requestBody)
                        .addHeader("apikey", supabaseAnonKey)
                        .addHeader("Authorization", "Bearer $supabaseAnonKey")
                        .addHeader("Content-Type", "application/json")
                        .build()

                    client.newCall(request).execute()
                }

                Log.d("CreateClient", "Response code: ${response.code}")
                val bodyText = response.body?.string()
                Log.d("CreateClient", "Response body: $bodyText")

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CreateClientActivity,
                        "Client account created successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@CreateClientActivity, DashBoardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@CreateClientActivity,
                        "Error: ${response.code} $bodyText",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {
                Log.e("CreateClient", "Exception: ${e.message}", e)
                Toast.makeText(
                    this@CreateClientActivity,
                    "Exception: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
