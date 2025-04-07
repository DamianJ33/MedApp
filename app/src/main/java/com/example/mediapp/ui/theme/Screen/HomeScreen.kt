package com.example.mediapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.mediapp.ui.theme.AuthViewModel

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(navController, authViewModel)

        // Zawartość ekranu głównego
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeButton(text = "Moje Wizyty") { navController.navigate("appointments") }
            HomeButton(text = "Historia Medyczna") { navController.navigate("history") }
            HomeButton(text = "Ustawienia") { navController.navigate("settings") }
        }
    }
}

@Composable
fun TopBar(navController: NavController, authViewModel: AuthViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "MediApp",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(onClick = {
            authViewModel.signout()
            navController.navigate("login") {
                popUpTo("login") { inclusive = true } // Usuwa historię nawigacji
            }
        }) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Wyloguj", tint = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text)
    }
}
