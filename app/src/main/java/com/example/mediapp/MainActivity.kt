package com.example.mediapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mediapp.Screen.LoginScreen
import com.example.mediapp.Screen.MainScreen
import com.example.mediapp.ui.theme.AuthViewModel
import com.example.mediapp.ui.theme.MediAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val authViewModel = hiltViewModel<AuthViewModel>()
            // Zainicjalizuj AuthViewModel z kontekstem
            val context = LocalContext.current // Uzyskaj dostęp do contextu w Compose
            authViewModel.init(context)

            val navController = rememberNavController()

            MediAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { paddingValues ->
                        MainScreen(
                            navController = navController,
                            authViewModel = authViewModel,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                )
            }
        }

    }
}
