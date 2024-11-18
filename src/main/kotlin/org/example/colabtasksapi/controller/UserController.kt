package org.example.colabtasksapi.controller

import org.example.colabtasksapi.dto.SignupDto
import org.example.colabtasksapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun registerUser(@RequestBody signupDto: SignupDto?): ResponseEntity<String> {
        return try {
            userService.registerUser(signupDto)
            ResponseEntity("Usuario registrado satisfactoriamente", HttpStatus.CREATED)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity("Registro de usuario fallido: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/users/{email}")
    fun updateUser(authentication: Authentication, @PathVariable email: String, @RequestBody updatedUser: SignupDto): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val user = userService.updateUser(email, updatedUser)
                ResponseEntity.ok(user)
            } catch (e: IllegalArgumentException) {
                ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
            } catch (e: RuntimeException) {
                ResponseEntity(e.message, HttpStatus.NOT_FOUND)
            } catch (e: Exception) {
                ResponseEntity("Actualización de usuario fallida: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @DeleteMapping("/users/{email}")
    fun deleteUser(authentication: Authentication, @PathVariable email: String): ResponseEntity<String> {
        return if (authentication.isAuthenticated) {
            try {
                userService.deleteUser(email)
                ResponseEntity("Usuario eliminado satisfactoriamente", HttpStatus.NO_CONTENT)
            } catch (e: RuntimeException) {
                ResponseEntity(e.message, HttpStatus.NOT_FOUND)
            } catch (e: Exception) {
                ResponseEntity("Eliminación de usuario fallida: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }
}