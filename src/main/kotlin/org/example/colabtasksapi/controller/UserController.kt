package org.example.colabtasksapi.controller

import org.example.colabtasksapi.dto.SignupDto
import org.example.colabtasksapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun registerUser(@RequestBody signupDto: SignupDto?): ResponseEntity<String> {
        return try {
            userService.registerUser(signupDto)
            ResponseEntity("Usuario registrado sactisfactoriamente", HttpStatus.CREATED)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Registro de usuario fallido: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}