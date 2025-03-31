package med.app.api.repository

import med.app.api.model.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppRepository : JpaRepository<Patient, String> {
    fun findByEmail(email: String): Patient?
}