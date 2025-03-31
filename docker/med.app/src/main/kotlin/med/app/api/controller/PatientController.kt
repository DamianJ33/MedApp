package med.app.api.controller

import med.app.api.model.Patient
import med.app.api.service.PatientService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/patients")
class PatientController(private val service: PatientService) {

    @GetMapping
    fun getAllPatients(): List<Patient> = service.getAllPatients()

    @GetMapping("/{email}")
    fun getPatientByEmail(@PathVariable email: String): Patient? = service.getPatientByEmail(email)

    @PostMapping
    fun addPatient(@RequestBody patient: Patient): Patient = service.addPatient(patient)


    @PostMapping("/register")
    fun registerPatient(
        @RequestHeader headers: Map<String, String>,
        @RequestBody patient: Patient
    ): ResponseEntity<Unit> {
        println("Headers: $headers")
        return ResponseEntity.ok().build()
    }
}
    }

