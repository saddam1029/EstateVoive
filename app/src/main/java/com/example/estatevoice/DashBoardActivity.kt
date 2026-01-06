package com.example.estatevoice

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.estatevoice.databinding.ActivityDashBoardBinding
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DashBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBoardBinding
    private val viewModel: CallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCurrentDate()

        showStatsLoading(true)   // ✅ show shimmer immediately

        val role = intent.getStringExtra("admin")

        if (!role.equals("ADMIN", ignoreCase = true)) {
            binding.btnCreateClient.visibility = View.GONE
        }

        // Load real data
        viewModel.loadCall()
        observeData()

        binding.btnCreateClient.setOnClickListener {
            startActivity(Intent(this, CreateClientActivity::class.java))
        }

        binding.cvLogs.setOnClickListener {
            startActivity(Intent(this, CallLogsActivity::class.java))
        }

        binding.ivLogout.setOnClickListener {
            logout()
        }
    }

    private fun showStatsLoading(show: Boolean) {
        if (show) {
            binding.shimmerStats.startShimmer()
            binding.shimmerStats.visibility = View.VISIBLE
            binding.constraintLayout10.visibility = View.GONE
        } else {
            binding.shimmerStats.stopShimmer()
            binding.shimmerStats.visibility = View.GONE
            binding.constraintLayout10.visibility = View.VISIBLE
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            // Observe loading state
            viewModel.loading.collect { isLoading ->
                showStatsLoading(isLoading)
            }
        }

        lifecycleScope.launch {
            // Observe call data
            viewModel.call.collect { calls ->
                if (calls.isNotEmpty()) {
                    val followUpCount = calls.count { it.followUpRequired }
//                        val qualifiedCount = calls.count { it.status == "QUALIFIED" }
//                        val notQualifiedCount = calls.count { it.status == "NOT_QUALIFIED" }

                    binding.tvTotalCalls.text = calls.size.toString()
                    binding.tvFollow.text = followUpCount.toString()
//                        binding.tvQualified.text = qualifiedCount.toString()
//                        binding.tvNotQualified.text = notQualifiedCount.toString()
                }
            }
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            try {
                SupabaseManager.client.gotrue.logout()

                val intent = Intent(this@DashBoardActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                finish()

            } catch (e: Exception) {
                Log.e("Logout", "Logout Error", e)
            }
        }
    }


    private fun showCurrentDate() {
        // Get current date
        val currentDate = Calendar.getInstance().time

        // Format: Jun 1, 2025
        val sdf = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)
        val formattedDate = sdf.format(currentDate)

        // Set text to TextView
        binding.tvCurrentDate.text = formattedDate
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, R.anim.slide_out_right)
    }
}


