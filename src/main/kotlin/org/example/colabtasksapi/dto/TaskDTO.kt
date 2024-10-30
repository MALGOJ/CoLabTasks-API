package org.example.colabtasksapi.dto

import org.example.colabtasksapi.model.TaskPriority
import org.example.colabtasksapi.model.TaskStatus
import java.time.LocalDateTime

data class TaskDTO(
    val idDto: Long,
    val titleDto: String,
    val descriptionDto: String,
    val statusDto: TaskStatus,
    val priorityDto: TaskPriority,
    val dueDateDto: LocalDateTime,
    val createdDateDto: LocalDateTime,
    val updatedDateDto: LocalDateTime,
    val proyectDto: ProyectDTO,
    val assignedUserDto: UserDTO? = null
)