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
    val createdDateDto: LocalDateTime? = LocalDateTime.now(),
    var updatedDateDto: LocalDateTime? = null,
    val proyectDto: ProyectDTO? = null,
    val assignedUserDto: UserDTO? = null
)

data class TaskResponseDTO(
    val id: Long,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val priority: TaskPriority,
    val dueDate: LocalDateTime,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime?,
    val proyect: ProyectDTO?,
    val assignedUser: AssignedUserResponseDTO?
)

data class AssignedUserResponseDTO(
    val email: String
)
