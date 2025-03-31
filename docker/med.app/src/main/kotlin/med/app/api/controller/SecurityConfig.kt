@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .antMatchers("/api/patients/register").permitAll() // Otwiera rejestrację
            .anyRequest()



        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        println("Błąd: ${e.message}") // Log do konsoli Dockera
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd serwera")
    }
}