package org.example.colabtasksapi.dto

import java.time.LocalDateTime

data class ProyectDTO(
    val idDto: Long,
    val nameDto: String,
    val descriptionDto: String,
    val startDateDto: LocalDateTime,
    val endDateDto: LocalDateTime,
    val createdDateDto: LocalDateTime,
    val tasksDto: List<TaskDTO> = mutableListOf(),
    val usersDto: List<UserDTO> = mutableListOf()
)