package com.example.estatevoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CallViewModel : ViewModel() {

    private val repository = CallRepository()

    private val _call = MutableStateFlow<List<ClientModel>>(emptyList())
    val call: StateFlow<List<ClientModel>> = _call

    private val _loading = MutableStateFlow(true) // start with true
    val loading: StateFlow<Boolean> = _loading

    fun loadCall() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _call.value = repository.fetchCall()
            } catch (e: Exception) {
                // Handle error if needed
            } finally {
                _loading.value = false
            }
        }
    }
}
