package com.example.estatevoice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(1500)

            // 1️⃣ Get current logged-in user
            val user = try {
                SupabaseManager.client.gotrue.currentUserOrNull()
            } catch (e: Exception) {
                Log.e("Splash", "Session error", e)
                null
            }

            // 2️⃣ Not logged in → Login
            if (user == null) {
                navigate(LoginActivity::class.java)
                return@launch
            }

            val userId = user.id

            // 3️⃣ Fetch role for logged-in user
            try {
                val role: List<UserRole> = SupabaseManager.client.postgrest
                    .from("users")
                    .select()
                    .decodeList()

                val currentId = role.find { it.id == userId }

                if (currentId?.role.equals("ADMIN", ignoreCase = true)){
                    navigate(CallLogsActivity::class.java)
                }else{
                    navigate(MainActivity::class.java)
                }

            } catch (e: Exception) {
                Log.e("Splash", "Role fetch error", e)
                navigate(LoginActivity::class.java)
            }
        }
    }

    private fun navigate(destination: Class<*>) {
        startActivity(Intent(this, destination))
        finish()
    }
}
