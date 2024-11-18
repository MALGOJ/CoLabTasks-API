package org.example.colabtasksapi.dto

import org.example.colabtasksapi.model.ReminderFrequency
import java.time.LocalDateTime

data class ReminderDTO(
    val idDto: Long,
    val frequencyDto: ReminderFrequency,
    val startDateDto: LocalDateTime,
    val endDateDto: LocalDateTime,
    val taskDto: TaskDTO,
    val userDto: UserDTO
)