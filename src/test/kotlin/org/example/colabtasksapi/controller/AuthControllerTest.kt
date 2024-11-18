package org.example.colabtasksapi.controller

import org.example.colabtasksapi.config.jwt.JwtUtil
import org.example.colabtasksapi.dto.LoginDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

class AuthControllerTest {

    private val authenticationManager: AuthenticationManager = mock(AuthenticationManager::class.java)
    private val jwtUtil: JwtUtil = mock(JwtUtil::class.java)

    private val authController = AuthController(authenticationManager, jwtUtil)

    @Test
    fun `login should return ResponseEntity with JWT in Authorization header`() {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        val jwtToken = "mockJwtToken"

        val loginDto = LoginDto(email, password)
        val authenticationToken = UsernamePasswordAuthenticationToken(email, password)

        val authentication: Authentication = mock(Authentication::class.java)

        `when`(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication)
        `when`(authentication.name).thenReturn(email)
        `when`(jwtUtil.create(email)).thenReturn(jwtToken)

        // Act
        val response: ResponseEntity<Void> = authController.login(loginDto)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(response.headers.containsKey(HttpHeaders.AUTHORIZATION))
        assertEquals(jwtToken, response.headers[HttpHeaders.AUTHORIZATION]?.get(0))

        // Verify interactions
        verify(authenticationManager).authenticate(authenticationToken)
        verify(jwtUtil).create(email)
    }
}
