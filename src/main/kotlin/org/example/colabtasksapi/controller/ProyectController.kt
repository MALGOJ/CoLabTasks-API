
package org.example.colabtasksapi.controller

import org.example.colabtasksapi.dto.ProyectDTO
import org.example.colabtasksapi.service.ProyectService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/projects")
class ProyectController(private val proyectService: ProyectService) {

    @GetMapping
    fun getAllProjectsByUser(authentication: Authentication): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val email = authentication.name
            val projects = proyectService.getAllProjectsByUser(email)
            ResponseEntity.ok(projects)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @GetMapping("/getProjectById/{id}")
    fun getProjectById(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val proyect = proyectService.getProjectById(id)
                ResponseEntity.ok(proyect)
            } catch (e: NullPointerException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @PostMapping("/saveProject")
    fun createProyect(authentication: Authentication, @RequestBody proyectDTO: ProyectDTO): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val userEmail = authentication.name
            val createdProyect = proyectService.createProject(proyectDTO, userEmail)
            ResponseEntity.status(HttpStatus.CREATED).body(createdProyect)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @PutMapping("/updateProject/{id}")
    fun updateProject(authentication: Authentication, @PathVariable id: Long, @RequestBody proyectDTO: ProyectDTO): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val updatedProject = proyectService.updateProject(id, proyectDTO)
                ResponseEntity.ok(updatedProject)
            } catch (e: RuntimeException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @DeleteMapping("/deleteProject/{id}")
    fun deleteProject(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                proyectService.deleteProject(id)
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            } catch (e: RuntimeException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }
}