package com.example.mediapp.ui.theme

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mediapp.Screen.User
import com.example.mediapp.Screen.UserDatabaseHelper
import com.example.mediapp.ui.theme.Screen.Patient
import com.example.mediapp.ui.theme.Screen.RetrofitClient

import kotlinx.coroutines.launch
import okhttp3.Credentials
import retrofit2.HttpException
import retrofit2.Retrofit

class AuthViewModel() : ViewModel() {

    sealed class AuthState {
        object Authenticated : AuthState() // User is logged in
        object Unauthenticated : AuthState() // User is not logged in
        object Loading : AuthState() // Authentication in progress
        data class Error(val message: String) : AuthState() // Error during authentication
    }

    val PatientData = mutableStateOf(Patient("","", ""))
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
    fun signup(name: String, email: String, password: String, navController: NavController) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            _authState.value = AuthState.Error("Pola nie mogą być puste")
            return
        }

        _authState.value = AuthState.Loading


        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.signup(Patient(name, email, password))

                // Sprawdzamy, czy odpowiedź HTTP jest udana (200 OK)
                if (response.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    navController.navigate("home")
                } else {
                    // Jeśli odpowiedź nie jest udana, zwracamy kod błędu
                    _authState.value = AuthState.Error("Błąd rejestracji: ${response.message()}")
                    println(response)
                }
            } catch (e: HttpException) {
                // Obsługa błędów HTTP (np. połączenie z serwerem)
                _authState.value = AuthState.Error("Błąd sieci: ${e.message()}")
            } catch (e: Exception) {
                // Ogólny błąd (np. połączenie z serwerem)
                _authState.value = AuthState.Error("Wystąpił nieoczekiwany błąd")
                println(e.message)
            }
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

     suspend fun getPatient(email: String){
        PatientData.value = RetrofitClient.apiService.GetPatient(email)
    }

    fun bookAppointment(
        date: String,
        time: String,
        reason: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val appointment = AppointmentRequest(date, time, reason)
                val response = apiService.bookAppointment(appointment)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Błąd serwera: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Błąd połączenia: ${e.message}")
            }
        }
    }


}
