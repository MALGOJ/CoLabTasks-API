package org.example.colabtasksapi.controller

import org.example.colabtasksapi.dto.ReminderDTO
import org.example.colabtasksapi.service.ReminderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reminders")
class ReminderController(private val reminderService: ReminderService) {

    @GetMapping
    fun getAllReminders(authentication: Authentication): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val reminders = reminderService.getAllReminders()
            ResponseEntity.ok(reminders)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @GetMapping("/{id}")
    fun getReminderById(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val reminder = reminderService.getReminderById(id)
                ResponseEntity.ok(reminder)
            } catch (e: NullPointerException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @PostMapping
    fun createReminder(authentication: Authentication, @RequestBody reminderDTO: ReminderDTO): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val createdReminder = reminderService.createReminder(reminderDTO)
            ResponseEntity.status(HttpStatus.CREATED).body(createdReminder)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteReminder(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                reminderService.deleteReminder(id)
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            } catch (e: RuntimeException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }
}