package com.example.mediapp.ui.theme.Screen

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PatientRepository {
    suspend fun GetPatient( email: String): Patient

    suspend fun signup(patient: Patient): Response<ResponseBody>

    suspend fun login(email: String, password: String): Response<ResponseBody>

    suspend fun bookAppointment(appointment: AppointmentRequest): Response<Void>
}