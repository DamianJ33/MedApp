package com.example.mediapp.ui.theme.Screen


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("/api/patients/{email}")
    suspend fun GetPatient(@Path("email") email: String): Patient
    @POST("/api/patients/register")
    suspend fun signup(@Body patient: Patient): Response<ResponseBody>
    @GET("/api/patients/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ResponseBody>
    @POST("/appointments")
    suspend fun bookAppointment(@Body appointment: AppointmentRequest): Response<Void>
}
