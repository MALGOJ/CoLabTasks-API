package org.example.colabtasksapi.controller

import org.example.colabtasksapi.dto.TaskDTO
import org.example.colabtasksapi.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    @GetMapping
    fun getAllTasks(authentication: Authentication): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val email = authentication.name
            val tasks = taskService.getAllTasksByUser(email)
            ResponseEntity.ok(tasks)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @GetMapping("/getTaskById/{id}")
    fun getTaskById(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val task = taskService.getTaskById(id)
                ResponseEntity.ok(task)
            } catch (e: NullPointerException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @PostMapping("/saveTask")
    fun createTask(authentication: Authentication, @RequestBody taskDTO: TaskDTO): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val createdTask = taskService.createTask(taskDTO, authentication.name)
            ResponseEntity.status(HttpStatus.CREATED).body(createdTask)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @PutMapping("/updateTaskById/{id}")
    fun updateTask(
        authentication: Authentication,
        @PathVariable id: Long,
        @RequestBody taskDTO: TaskDTO
    ): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val updatedTask = taskService.updateTask(id, taskDTO)
                ResponseEntity.ok(updatedTask)
            } catch (e: RuntimeException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @DeleteMapping("/deleteTask/{id}")
    fun deleteTask(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                taskService.deleteTask(id)
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            } catch (e: RuntimeException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }
}