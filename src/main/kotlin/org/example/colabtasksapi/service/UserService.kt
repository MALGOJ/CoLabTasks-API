package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.SignupDto
import org.example.colabtasksapi.model.User
import org.example.colabtasksapi.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Service class for managing users.
 *
 * @property userRepository Repository for user data.
 * @property passwordEncoder Encoder for user passwords.
 */
@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    /**
     * Checks if an email already exists in the repository.
     *
     * @param email The email to check.
     * @return True if the email exists, false otherwise.
     */
    fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }

    /**
     * Registers a new user.
     *
     * @param signupDto The data transfer object containing user registration details.
     * @return The registered User object.
     * @throws IllegalArgumentException if the signupDto is null or contains invalid data.
     */
    fun registerUser(signupDto: SignupDto?): User {
        if (signupDto == null) {
            throw IllegalArgumentException("Registration data cannot be null")
        }

        if (signupDto.email.isBlank()) {
            throw IllegalArgumentException("Email cannot be blank")
        }
        if (signupDto.name.isBlank()) {
            throw IllegalArgumentException("Name cannot be blank")
        }
        if (signupDto.password.isBlank()) {
            throw IllegalArgumentException("Password cannot be blank")
        }

        if (emailExists(signupDto.email)) {
            throw IllegalArgumentException("User with email ${signupDto.email} is already registered")
        }

        val user = User(
            email = signupDto.email,
            name = signupDto.name,
            password = passwordEncoder.encode(signupDto.password),
            locked = false,
            disabled = false,
            createDate = LocalDate.now(),
            proyects = mutableListOf()
        )
        return userRepository.save(user)
    }

    /**
     * Updates an existing user.
     *
     * @param email The email of the user to be updated.
     * @param updatedUser The data transfer object containing updated user details.
     * @return The updated User object.
     * @throws RuntimeException if the user is not found.
     * @throws IllegalArgumentException if the updated user details are invalid.
     */
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

    /**
     * Deletes a user by their email.
     *
     * @param email The email of the user to be deleted.
     * @throws RuntimeException if the user is not found.
     */
    @Transactional
    fun deleteUser(email: String) {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException("User not found")
        userRepository.delete(user)
    }
}