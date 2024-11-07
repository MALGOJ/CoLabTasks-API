package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.SignupDto
import org.example.colabtasksapi.model.User
import org.example.colabtasksapi.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }

    fun registerUser(signupDto: SignupDto?): User {
        // Validar si signupDto es null
        if (signupDto == null) {
            throw IllegalArgumentException("Los datos de registro no pueden ser nulos")
        }

        // Validar si el email, name o password están vacíos
        if (signupDto.email.isBlank()) {
            throw IllegalArgumentException("El email no puede estar vacío")
        }
        if (signupDto.name.isBlank()) {
            throw IllegalArgumentException("El nombre no puede estar vacío")
        }
        if (signupDto.password.isBlank()) {
            throw IllegalArgumentException("La contraseña no puede estar vacía")
        }

        // Validar si el email ya existe
        if (emailExists(signupDto.email)) {
            throw IllegalArgumentException("El usuario con el email ${signupDto.email} ya está registrado")
        }

        val user = User(
            email = signupDto.email,
            name = signupDto.name,
            password = passwordEncoder.encode(signupDto.password),
            locked = false,
            disabled = false,
            createDate = LocalDate.now(),
            proyects = mutableListOf() //inicializa la lista en vacio para que no sea null y agregar prooyectos a futuro
        )
        return userRepository.save(user)
    }

    @Transactional
    fun updateUser(email: String, updatedUser: SignupDto): User {
        val existingUser = userRepository.findByEmail(email) ?: throw RuntimeException("User not found")

        if (updatedUser.email.isBlank() || updatedUser.name.isBlank() || updatedUser.password.isBlank()) {
            throw IllegalArgumentException("User details cannot be blank")
        }

        val user = existingUser.copy(
            name = updatedUser.name,
            password = passwordEncoder.encode(updatedUser.password)
        )

        return userRepository.save(user)
    }

    @Transactional
    fun deleteUser(email: String) {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException("User not found")
        userRepository.delete(user)
    }
}