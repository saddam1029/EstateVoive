package com.example.estatevoice

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estatevoice.databinding.ActivityCallLogsBinding
import kotlinx.coroutines.launch

class CallLogsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCallLogsBinding
    private val viewModel: CallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallLogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCallLogs.layoutManager = LinearLayoutManager(this)

        fetchClient()

    }

    private fun fetchClient() {
        lifecycleScope.launch {
            viewModel.call.collect {
                binding.rvCallLogs.adapter = CallAdapter(it)
            }
        }

        viewModel.loadCall()
    }
}