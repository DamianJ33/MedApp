package med.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import med.app.api.service.PatientService
import org.springframework.boot.CommandLineRunner

@SpringBootApplication
class Application(val service: PatientService): CommandLineRunner{
	override fun run(vararg arg: String?) {
		service.getAllPatients()
	}
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}