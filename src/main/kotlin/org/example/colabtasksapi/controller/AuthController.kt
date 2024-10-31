package org.example.colabtasksapi.controller

import org.example.colabtasksapi.config.jwt.JwtUtil
import org.example.colabtasksapi.dto.LoginDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/auth")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<Void> {

        val login = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        val authentication = authenticationManager.authenticate(login)
        val jwt = jwtUtil.create(authentication.name)

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build()
    }
}