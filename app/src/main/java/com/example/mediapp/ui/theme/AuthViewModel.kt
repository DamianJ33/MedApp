package com.example.mediapp.ui.theme

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mediapp.Screen.User
import com.example.mediapp.Screen.UserDatabaseHelper

class AuthViewModel() : ViewModel() {

    sealed class AuthState {
        object Authenticated : AuthState() // User is logged in
        object Unauthenticated : AuthState() // User is not logged in
        object Loading : AuthState() // Authentication in progress
        data class Error(val message: String) : AuthState() // Error during authentication
    }

    private lateinit var context: Context
    private lateinit var userDatabaseHelper: UserDatabaseHelper // Używamy lateinit do późniejszej inicjalizacji
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    // Inicjalizacja AuthViewModel
    fun init(context: Context) {
        this.context = context
        userDatabaseHelper = UserDatabaseHelper()
    }

}
