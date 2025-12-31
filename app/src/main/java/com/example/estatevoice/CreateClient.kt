package com.example.estatevoice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.estatevoice.databinding.ActivityCreateClientBinding
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class CreateClient : AppCompatActivity() {
    lateinit var binding: ActivityCreateClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            accountCreation()
        }
    }
}

private fun CreateClient.accountCreation() {
    val email = binding.etEmail.text.toString().trim()
    val password = binding.etPassword.text.toString().trim()
    val conPassword = binding.etConfirmPassword.text.toString().trim()

    if (email.isEmpty() || password.isEmpty() || conPassword.isEmpty()) {
        Toast.makeText(this, "Please Enter Email & Password", Toast.LENGTH_SHORT).show()
    }

    if (password != conPassword) {
        Toast.makeText(this, "Passwords are not Same!!", Toast.LENGTH_SHORT).show()
    }

    lifecycleScope.launch {
        try {
            SupabaseManager.client.gotrue.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            Toast.makeText(
                this@accountCreation,
                "Client Account Created Successfully",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this@accountCreation, CallLogsActivity::class.java))
            finish()

        } catch (e: Exception) {
            Toast.makeText(this@accountCreation, "Error ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("AccountCreation", "${e.message}")
        }
    }
}
