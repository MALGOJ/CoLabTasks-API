package org.example.colabtasksapi.service

import org.example.colabtasksapi.model.User
import org.example.colabtasksapi.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class UserSecurityServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userSecurityService: UserSecurityService

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User("test@example.com", "password", "Test User", LocalDate.now(), true, false, mutableListOf())
    }

    @Test
    fun loadUserByUsername_UserExists() {
        `when`(userRepository.findByEmail("test@example.com")).thenReturn(user)

        val userDetails = userSecurityService.loadUserByUsername("test@example.com")

        assertEquals("test@example.com", userDetails.username)
        assertEquals("password", userDetails.password)
        assertTrue(userDetails.isEnabled)
        assertFalse(userDetails.isAccountNonLocked)
        verify(userRepository, times(1)).findByEmail("test@example.com")
    }

    @Test
    fun loadUserByUsername_UserNotFound() {
        `when`(userRepository.findByEmail("unknown@example.com")).thenReturn(null)

        val exception = assertThrows(UsernameNotFoundException::class.java) {
            userSecurityService.loadUserByUsername("unknown@example.com")
        }

        assertEquals("User not found with email: unknown@example.com", exception.message)
        verify(userRepository, times(1)).findByEmail("unknown@example.com")
    }
}