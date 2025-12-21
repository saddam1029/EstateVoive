package com.example.estatevoice

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SupabaseClient.initialize(this)
    }
}
