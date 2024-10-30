package org.example.colabtasksapi.util

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Bean
fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
}

//verifica la autenticacion
@Bean
fun authenticationManager(
    authenticationConfiguration: AuthenticationConfiguration
): AuthenticationManager {
    return authenticationConfiguration.authenticationManager
}