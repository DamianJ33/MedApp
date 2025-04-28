package com.example.mediapp.ui.theme.Screen

import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class PatientRepositoryImp @Inject constructor(
    private val api: ApiService
) : PatientRepository {
    override suspend fun GetPatient( email: String): Patient{
        return api.GetPatient(email)
    }
    override suspend fun signup(patient: Patient): Response<ResponseBody>{
        return api.signup(patient)
    }

    override suspend fun login(email: String, password: String): Response<ResponseBody> {
        return api.login(email, password)
    }

    override suspend fun bookAppointment(appointment: AppointmentRequest): Response<Void>{
        return api.bookAppointment(appointment)
    }
}