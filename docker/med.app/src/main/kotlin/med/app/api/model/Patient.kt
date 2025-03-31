package med.app.api.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class Patient(
    val name: String,
    @Id
    val email: String,
    val password: String,
)