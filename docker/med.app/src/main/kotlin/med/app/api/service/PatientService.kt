package med.app.api.service


import med.app.api.model.Patient
import med.app.api.repository.AppRepository
import org.springframework.stereotype.Service

@Service
class PatientService(private val repository: AppRepository) {

    fun getAllPatients(): List<Patient> = repository.findAll()

    fun getPatientByEmail(email: String): Patient? = repository.findByEmail(email)

    fun addPatient(patient: Patient): Patient = repository.save(patient)

    fun registerPatient(patient: Patient): Patient {
        if (repository.findByEmail(patient.email) != null) {
            throw IllegalArgumentException("Email already exists")
        }

        val newPatient = patient.copy(password = patient.password)

        return repository.save(newPatient)
    }
}