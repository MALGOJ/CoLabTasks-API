package org.example.colabtasksapi.service

import org.example.colabtasksapi.model.User
import org.example.colabtasksapi.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserSecurityService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.email)
            .password(user.password)
            .disabled(user.disabled)
            .accountLocked(user.locked)
            .authorities(emptyList())
            .build()
    }
}