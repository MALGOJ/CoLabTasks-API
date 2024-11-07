package org.example.colabtasksapi.controller

import org.example.colabtasksapi.dto.ProyectDTO
import org.example.colabtasksapi.service.ProyectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/proyects")
class ProyectController(private val proyectService: ProyectService) {

    @GetMapping
    fun getAllProyects(authentication: Authentication): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val proyects = proyectService.getAllProyects()
            ResponseEntity.ok(proyects)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @GetMapping("/{id}")
    fun getProyectById(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val proyect = proyectService.getProyectById(id)
                ResponseEntity.ok(proyect)
            } catch (e: NullPointerException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @PostMapping
    fun createProyect(authentication: Authentication, @RequestBody proyectDTO: ProyectDTO): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val createdProyect = proyectService.createProyect(proyectDTO)
            ResponseEntity.status(HttpStatus.CREATED).body(createdProyect)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @PutMapping("/{id}")
    fun updateProyect(authentication: Authentication, @PathVariable id: Long, @RequestBody proyectDTO: ProyectDTO): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val updatedProyect = proyectService.updateProyect(id, proyectDTO)
                ResponseEntity.ok(updatedProyect)
            } catch (e: RuntimeException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteProyect(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                proyectService.deleteProyect(id)
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            } catch (e: RuntimeException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }
}