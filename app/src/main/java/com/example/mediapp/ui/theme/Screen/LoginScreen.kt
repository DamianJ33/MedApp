package com.example.mediapp.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mediapp.ui.theme.AuthViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Validate email format
    fun isEmailValid(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    // Validate password (e.g., minimum 6 characters)
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login Page", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            isError = !isEmailValid(email) && email.isNotEmpty(),
            supportingText = {
                if (!isEmailValid(email) && email.isNotEmpty()) {
                    Text(text = "Invalid email format", color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            isError = !isPasswordValid(password) && password.isNotEmpty(),
            supportingText = {
                if (!isPasswordValid(password) && password.isNotEmpty()) {
                    Text(text = "Password must be at least 6 characters", color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isEmailValid(email) && isPasswordValid(password)) {
                    isLoading = true
                    scope.launch {
                        try {
                            val user = authViewModel.getUserByEmail(email)
                            if (user != null && user.password == password) {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true } // This will clear the back stack
                                }
                            } else {
                                Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show()
                        } finally {
                            isLoading = false
                        }
                    }
                } else {
                    Toast.makeText(context, "Please enter valid email and password", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = !isLoading && isEmailValid(email) && isPasswordValid(password)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(text = "Login")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("signup") }) {
            Text(text = "Don't have an account? Sign up")
        }
    }
}
