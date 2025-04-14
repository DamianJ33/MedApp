package com.example.mediapp.ui.theme.Screen

data class Patient(
    val name: String,
    val email: String,
    val password: String,
)

data class LoginRequest(
    val email: String,
    val password: String,
)

data class AppointmentRequest(
    val date: String,
    val time: String,
    val reason: String
)