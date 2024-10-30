package org.example.colabtasksapi.service

import org.example.colabtasksapi.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserSecurityService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    private val logger = LoggerFactory.getLogger(UserSecurityService::class.java)

    override fun loadUserByUsername(email: String?): UserDetails {
        val user = userRepository.findById(email!!).orElseThrow { Exception("User not found") }
        logger.info(":::::: $user")
        return User.builder()
            .username(user.email)
            .password(user.password)
            .disabled(user.disabled)
            .accountLocked(user.locked)
            .build()
    }
}