package com.example.estatevoice

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SupabaseManager.initialize(this)
    }
}
