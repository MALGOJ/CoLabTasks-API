package org.example.colabtasksapi.dto

import java.time.LocalDateTime

data class ProyectDTO(
    val idDto: Long,
    val nameDto: String,
    val descriptionDto: String,
    val startDateDto: LocalDateTime,
    val endDateDto: LocalDateTime,
    val createdDateDto: LocalDateTime? = LocalDateTime.now(),
    val tasksDto: List<TaskDTO>? = mutableListOf(),
    val usersDto: List<UserEmailDto> = mutableListOf()
)

data class ProjectResponseDTO(
    val name: String,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val createdDate: LocalDateTime,
    val tasks: List<TaskDTO>,
    val users: List<UserProjectDTO>
)

data class UserProjectDTO(
    val email: String
)