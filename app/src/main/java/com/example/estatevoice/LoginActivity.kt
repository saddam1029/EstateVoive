package com.example.estatevoice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.estatevoice.databinding.ActivityLoginBinding
import io.github.jan.supabase.gotrue.gotrue  // Use 'gotrue' instead of 'auth'
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                // Login user
                val result = SupabaseManager.client.gotrue.loginWith(Email) {
                    this.email = email
                    this.password = password
                }

                val currentUser = SupabaseManager.client.gotrue.currentSessionOrNull()?.user
                val userId = currentUser?.id


                if (userId == null) {
                    Toast.makeText(this@LoginActivity, "User ID not found", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // Fetch role from 'users' table
                    val roleList: List<UserModel> = SupabaseManager.client.postgrest
                    .from("users")
                    .select()
                    .decodeList()


                val currentId = roleList.find { it.id == userId }
                // decode as List<UserRole>

                if (currentId?.role.equals("ADMIN", ignoreCase = true)) {
                    val intent = Intent(this@LoginActivity, DashBoardActivity::class.java)
                    intent.putExtra("currentId", currentId.toString())
                    intent.putExtra("admin", "ADMIN")
                    startActivity(intent)
                    Log.e("ADMIN", "Login Failed: $currentId")

                } else {
                    startActivity(Intent(this@LoginActivity, DashBoardActivity::class.java))
                }

            } catch (e: Exception) {
                Log.e("login", "Login Failed: ${e.message}", e)
                when {
                    e.message?.contains("Email not confirmed", true) == true ->
                        Toast.makeText(
                            this@LoginActivity,
                            "Please confirm your email before logging in.",
                            Toast.LENGTH_LONG
                        ).show()

                    e.message?.contains("Invalid login credentials", true) == true ->
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid email or password.",
                            Toast.LENGTH_LONG
                        ).show()

                    else -> Toast.makeText(
                        this@LoginActivity,
                        "Login Failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}