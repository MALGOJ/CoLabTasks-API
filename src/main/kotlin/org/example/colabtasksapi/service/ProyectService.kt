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

/**
 * Service class for managing projects.
 *
 * @property proyectRepository Repository for project data.
 * @property proyectMapper Mapper for converting between project entities and DTOs.
 * @property userRepository Repository for user data.
 * @property taskMapper Mapper for converting between task entities and DTOs.
 * @property userMapper Mapper for converting between user entities and DTOs.
 */
@Service
class ProyectService(
    private val proyectRepository: ProyectRepository,
    private val proyectMapper: ProyectMapper,
    private val userRepository: UserRepository,
    private val taskMapper: TaskMapper,
    private val userMapper: UserMapper
) {

    /**
     * Retrieves all projects associated with a specific user.
     *
     * @param email The email of the user.
     * @return A list of ProjectResponseDTO objects representing the user's projects.
     * @throws NullPointerException if no projects are found.
     */
    @Transactional(readOnly = true)
    fun getAllProjectsByUser(email: String): List<ProjectResponseDTO> {
        println("el email es: $email")
        val proyects = proyectRepository.findAllByUserEmail(email)
        if (proyects.isEmpty()) {
            throw NullPointerException("No hay proyectos definidos")
        }
        return proyects.map { proyectMapper.toProjectResponseDTO(it) }
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id The ID of the project.
     * @return A ProyectDTO object representing the project.
     * @throws NullPointerException if the project is not found.
     */
    @Transactional(readOnly = true)
    fun getProjectById(id: Long): ProyectDTO {
        val project = proyectRepository.findById(id).orElseThrow { NullPointerException("Project not found") }
        return proyectMapper.toProyectDTO(project)
    }

    /**
     * Creates a new project.
     *
     * @param proyectDTO The data transfer object containing project details.
     * @param userEmail The email of the user creating the project.
     * @throws RuntimeException if the user is not found.
     */
    @Transactional
    fun createProject(proyectDTO: ProyectDTO, userEmail: String) {
        val user = userRepository.findByEmail(userEmail) ?: throw RuntimeException("User not found")
        val proyect = proyectMapper.toProyect(proyectDTO).apply {
            if (users.isEmpty()) {
                users = mutableListOf(user)
            }
        }
        proyectRepository.save(proyect)
    }

    /**
     * Updates an existing project.
     *
     * @param id The ID of the project to be updated.
     * @param updatedProyectDTO The data transfer object containing updated project details.
     * @return A ProjectResponseDTO object representing the updated project.
     * @throws RuntimeException if the project is not found.
     */
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

    /**
     * Deletes a project by its ID.
     *
     * @param id The ID of the project to be deleted.
     * @throws RuntimeException if the project is not found.
     */
    @Transactional
    fun deleteProject(id: Long) {
        if (!proyectRepository.existsById(id)) {
            throw RuntimeException("Proyect not found")
        }
        proyectRepository.deleteById(id)
    }
}