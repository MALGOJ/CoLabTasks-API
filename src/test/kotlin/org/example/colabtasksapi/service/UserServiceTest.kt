package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.SignupDto
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
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var userService: UserService

    private lateinit var user: User
    private lateinit var signupDto: SignupDto

    @BeforeEach
    fun setUp() {
        user = User("test@example.com", "password", "Test User", LocalDate.now(), false, false, mutableListOf())
        signupDto = SignupDto("test@example.com", "Test User", "password")
    }

    @Test
    fun emailExists() {
        `when`(userRepository.findByEmail("test@example.com")).thenReturn(user)

        val exists = userService.emailExists("test@example.com")

        assertTrue(exists)
        verify(userRepository, times(1)).findByEmail("test@example.com")
    }

    @Test
    fun registerUser() {
        `when`(userRepository.findByEmail("test@example.com")).thenReturn(null)
        `when`(passwordEncoder.encode("password")).thenReturn("encodedPassword")
        val encodedUser = user.copy(password = "encodedPassword")
        `when`(userRepository.save(any(User::class.java))).thenReturn(encodedUser)

        val registeredUser = userService.registerUser(signupDto)

        assertNotNull(registeredUser)
        assertEquals("test@example.com", registeredUser.email)
        assertEquals("Test User", registeredUser.name)
        assertEquals("encodedPassword", registeredUser.password)
        verify(userRepository, times(1)).findByEmail("test@example.com")
        verify(passwordEncoder, times(1)).encode("password")
        verify(userRepository, times(1)).save(any(User::class.java))
    }

    @Test
    fun updateUser() {
        `when`(userRepository.findByEmail("test@example.com")).thenReturn(user)
        `when`(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword")
        val updatedUser = user.copy(name = "New User", password = "encodedNewPassword")
        `when`(userRepository.save(any(User::class.java))).thenReturn(updatedUser)

        val result = userService.updateUser("test@example.com", SignupDto("test@example.com", "New User", "newPassword"))

        assertNotNull(result)
        assertEquals("New User", result.name)
        assertEquals("encodedNewPassword", result.password)
        verify(userRepository, times(1)).findByEmail("test@example.com")
        verify(passwordEncoder, times(1)).encode("newPassword")
        verify(userRepository, times(1)).save(any(User::class.java))
    }

    @Test
    fun deleteUser() {
        `when`(userRepository.findByEmail("test@example.com")).thenReturn(user)

        userService.deleteUser("test@example.com")

        verify(userRepository, times(1)).findByEmail("test@example.com")
        verify(userRepository, times(1)).delete(user)
    }
}