package org.example.colabtasksapi.controller

import org.example.colabtasksapi.dto.NotificationDTO
import org.example.colabtasksapi.service.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notifications")
class NotificationController(private val notificationService: NotificationService) {

    @GetMapping
    fun getAllNotifications(authentication: Authentication): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val notifications = notificationService.getAllNotifications()
            ResponseEntity.ok(notifications)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @GetMapping("/{id}")
    fun getNotificationById(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                val notification = notificationService.getNotificationById(id)
                ResponseEntity.ok(notification)
            } catch (e: NullPointerException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @PostMapping
    fun createNotification(authentication: Authentication, @RequestBody notificationDTO: NotificationDTO): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            val createdNotification = notificationService.createNotification(notificationDTO)
            ResponseEntity.status(HttpStatus.CREATED).body(createdNotification)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }

    @DeleteMapping("/{id}")
    fun deleteNotification(authentication: Authentication, @PathVariable id: Long): ResponseEntity<*> {
        return if (authentication.isAuthenticated) {
            try {
                notificationService.deleteNotification(id)
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            } catch (e: RuntimeException) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }
}