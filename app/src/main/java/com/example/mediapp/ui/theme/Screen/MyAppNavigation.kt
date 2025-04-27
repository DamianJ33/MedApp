package com.example.mediapp.Screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable



import com.example.mediapp.ui.theme.AuthViewModel
import com.example.mediapp.ui.theme.Screen.AppointmentScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navController: NavHostController) {


    NavHost(navController = navController, startDestination = "login", modifier = modifier) {
        composable("login") { LoginScreen(modifier = modifier, navController = navController, authViewModel = authViewModel) }
        composable("signup") { SignupScreen(modifier = modifier, navController = navController, authViewModel = authViewModel) }
        composable("home") { HomeScreen(navController, authViewModel) }
        composable("appointments") { AppointmentScreen(navController, authViewModel) }
//        composable("appointmentHistory") { AppointmentListScreen(navController, authViewModel) }
//        composable("profile") { ProfileScreen(navController, authViewModel) }
    }
}


data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

