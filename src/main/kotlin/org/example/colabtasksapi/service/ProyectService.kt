package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.ProjectResponseDTO
import org.example.colabtasksapi.dto.ProyectDTO
import org.example.colabtasksapi.mappers.ProyectMapper
import org.example.colabtasksapi.mappers.TaskMapper
import org.example.colabtasksapi.mappers.UserMapper
import org.example.colabtasksapi.model.Proyect
import org.example.colabtasksapi.repository.ProyectRepository
import org.example.colabtasksapi.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProyectService(
    private val proyectRepository: ProyectRepository,
    private val proyectMapper: ProyectMapper,
    private val userRepository: UserRepository,
    private val taskMapper: TaskMapper,
    private val userMapper: UserMapper
) {

    @Transactional(readOnly = true)
    fun getAllProjectsByUser(email: String): List<ProjectResponseDTO> {
        println("el email es: $email")
        val proyects = proyectRepository.findAllByUserEmail(email)
        if (proyects.isEmpty()) {
            throw NullPointerException("No hay proyectos definidos")
        }
        return proyects.map { proyectMapper.toProjectResponseDTO(it) }
    }

    @Transactional(readOnly = true)
    fun getProjectById(id: Long): ProyectDTO {
        val project = proyectRepository.findById(id).orElseThrow { NullPointerException("Project not found") }
        return proyectMapper.toProyectDTO(project)
    }

    @Transactional
    fun createProject(proyectDTO: ProyectDTO, userEmail: String) {
        val user = userRepository.findByEmail(userEmail) ?: throw RuntimeException("User not found") //Busdca el usuario por email en la base de datos
        val proyect = proyectMapper.toProyect(proyectDTO).apply { //Mapea el DTO a la entidad Proyect
            if (users.isEmpty()) {
                users = mutableListOf(user) // Si la lista de usuarios es nula o vacía, se asigna el usuario actual
            }
        }
        proyectRepository.save(proyect) //Guarda el proyecto en la base de datos
    }

    @Transactional
    fun updateProject(id: Long, updatedProyectDTO: ProyectDTO): ProjectResponseDTO {
        val existingProyect = proyectRepository.findById(id).orElseThrow { RuntimeException("Proyect not found") }

        existingProyect.apply {
            this.name = updatedProyectDTO.nameDto
            this.description = updatedProyectDTO.descriptionDto
            this.startDate = updatedProyectDTO.startDateDto
            this.endDate = updatedProyectDTO.endDateDto
            this.tasks = (this.tasks + (updatedProyectDTO.tasksDto?.map { taskMapper.toTask(it) } ?: emptyList())).distinct().toMutableList()
            this.users = (this.users + updatedProyectDTO.usersDto.map { userMapper.toUserEmail(it) }).distinct().toMutableList()
        }

        val savedProyect = proyectRepository.save(existingProyect)
        return proyectMapper.toProjectResponseDTO(savedProyect)
    }

    @Transactional
    fun deleteProject(id: Long) {
        if (!proyectRepository.existsById(id)) {
            throw RuntimeException("Proyect not found")
        }
        proyectRepository.deleteById(id)
    }
}