package com.example.mediapp.ui.theme.Screen

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun ProvidePatientRepository(api: ApiService): PatientRepository {
        return PatientRepositoryImp(api)
    }
}