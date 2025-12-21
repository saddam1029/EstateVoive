package com.example.estatevoice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.estatevoice.databinding.ActivityCallLogsBinding

class CallLogs : AppCompatActivity() {
    private lateinit var binding : ActivityCallLogsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCallLogsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}