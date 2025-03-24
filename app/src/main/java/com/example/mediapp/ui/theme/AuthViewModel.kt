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

    fun checkAuthStatus(userId: Int) {
        if (userId == -1) {
            _authState.value = AuthState.Unauthenticated
        } else {
            val user = userDatabaseHelper.getUser(userId) // Używamy już zainicjalizowanej instancyjnej zmiennej
            if (user == null) {
                _authState.value = AuthState.Unauthenticated
            } else {
                _authState.value = AuthState.Authenticated
            }
        }
    }

    fun saveUserIdToSession(userId: Int) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("user_id", userId)  // Zapisz userId
        editor.apply()
    }



    // Logowanie użytkownika
    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email lub hasło nie mogą być puste")
            return
        }

        _authState.value = AuthState.Loading

        val user = userDatabaseHelper.getUserByEmail(email)
        if (user != null && user.password == password) {
            // Zapisz userId w SharedPreferences
            saveUserIdToSession(user.id)
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Error("Błędny email lub hasło")
        }
    }

    // Rejestracja użytkownika
    fun signup(email: String, password: String, navController: NavController) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email lub hasło nie mogą być puste")
            return
        }

        _authState.value = AuthState.Loading

        val userExists = userDatabaseHelper.checkUserExistence(email)
        if (userExists) {
            _authState.value = AuthState.Error("Użytkownik już istnieje")
            return
        }

        // Dodajemy użytkownika do bazy danych
        val isUserAdded = userDatabaseHelper.addUser(email, password)
        if (isUserAdded) {
            val newUser = userDatabaseHelper.getUserByEmail(email) // Pobierz nowo dodanego użytkownika
            newUser?.let {
                // Zapisz userId nowego użytkownika w SharedPreferences
                saveUserIdToSession(it.id)
                _authState.value = AuthState.Authenticated
                navController.navigate("home")
            }
        } else {
            _authState.value = AuthState.Error("Błąd przy tworzeniu konta")
        }
    }

    // Wylogowanie użytkownika
    fun signout() {
        // Usuń userId z SharedPreferences
        clearUserFromSession()
        _authState.value = AuthState.Unauthenticated
    }

    // Usuń userId z SharedPreferences
    fun clearUserFromSession() {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("user_id")  // Usuń userId
        editor.apply()
    }

    // Funkcja getUserByEmail (już dodana w klasie UserDatabaseHelper)
    fun getUserByEmail(email: String): User? {
        return userDatabaseHelper.getUserByEmail(email)
    }


}
