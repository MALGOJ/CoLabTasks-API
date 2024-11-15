package org.example.colabtasksapi.dto

import java.time.LocalDate

data class UserDTO(
    val emailDto: String,
    val nameDto: String,
    val createDateDto: LocalDate,
    val passwordDto: String,
    val lockedDto: Boolean,
    val disabledDto: Boolean,
    val proyectsDto: List<ProyectDTO> = mutableListOf()
)

data class UserEmailDto(
    val email: String
)