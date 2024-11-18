package org.example.colabtasksapi.dto

import org.example.colabtasksapi.model.NotificationType
import java.time.LocalDateTime

data class NotificationDTO(
    val idDto: Long,
    val messageDto: String,
    val typeDto: NotificationType,
    val sendDateDto: LocalDateTime,
    val taskDto: TaskDTO
)