package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.TaskDTO
import org.example.colabtasksapi.dto.TaskResponseDTO
import org.example.colabtasksapi.dto.UserDTO
import org.example.colabtasksapi.mappers.TaskMapper
import org.example.colabtasksapi.model.Task
import org.example.colabtasksapi.repository.TaskRepository
import org.example.colabtasksapi.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Service class for managing tasks.
 *
 * @property taskRepository Repository for task data.
 * @property taskMapper Mapper for converting between task entities and DTOs.
 * @property userRepository Repository for user data.
 */
@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val taskMapper: TaskMapper,
    private val userRepository: UserRepository,
) {

    /**
     * Retrieves all tasks associated with a specific user.
     *
     * @param email The email of the user.
     * @return A list of TaskResponseDTO objects representing the user's tasks.
     */
    @Transactional(readOnly = true)
    fun getAllTasksByUser(email: String): List<TaskResponseDTO> {
        val tasks = taskRepository.findAllByUserEmail(email)
        return tasks.map { taskMapper.toTaskResponseDTO(it) }
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task.
     * @return A TaskResponseDTO object representing the task.
     * @throws NullPointerException if the task is not found.
     */
    @Transactional(readOnly = true)
    fun getTaskById(id: Long): TaskResponseDTO {
        val task = taskRepository.findById(id).orElseThrow { NullPointerException("Task not found") }
        return taskMapper.toTaskResponseDTO(task)
    }

    /**
     * Creates a new task.
     *
     * @param taskDTO The data transfer object containing task details.
     * @param email The email of the user to whom the task is assigned.
     * @return A TaskResponseDTO object representing the created task.
     * @throws IllegalArgumentException if the task title is blank or the user is not found.
     */
    @Transactional
    fun createTask(taskDTO: TaskDTO, email: String): TaskResponseDTO {
        if (taskDTO.titleDto.isBlank()) {
            throw IllegalArgumentException("Task title cannot be blank")
        }

        val assignedUser = email.let { userRepository.findByEmail(it) }
            ?: throw IllegalArgumentException("User not found for email: $email")

        val task = taskMapper.toTask(taskDTO).apply {
            this.assignedUser = assignedUser
        }

        val savedTask = taskRepository.save(task)
        return taskMapper.toTaskResponseDTO(savedTask)
    }

    /**
     * Updates an existing task.
     *
     * @param id The ID of the task to be updated.
     * @param updatedTaskDTO The data transfer object containing updated task details.
     * @return A TaskResponseDTO object representing the updated task.
     * @throws RuntimeException if the task is not found.
     * @throws IllegalArgumentException if the task title is blank.
     */
    @Transactional
    fun updateTask(id: Long, updatedTaskDTO: TaskDTO): TaskResponseDTO {
        val existingTask = taskRepository.findById(id).orElseThrow { RuntimeException("Task not found") }

        if (updatedTaskDTO.titleDto.isBlank()) {
            throw IllegalArgumentException("Task title cannot be blank")
        }

        existingTask.apply {
            this.title = updatedTaskDTO.titleDto
            this.description = updatedTaskDTO.descriptionDto
            this.status = updatedTaskDTO.statusDto
            this.priority = updatedTaskDTO.priorityDto
            this.dueDate = updatedTaskDTO.dueDateDto
            this.updatedDate = updatedTaskDTO.updatedDateDto ?: LocalDateTime.now()
        }

        val savedTask = taskRepository.save(existingTask)
        return taskMapper.toTaskResponseDTO(savedTask)
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to be deleted.
     * @throws RuntimeException if the task is not found.
     */
    @Transactional
    fun deleteTask(id: Long) {
        if (!taskRepository.existsById(id)) {
            throw RuntimeException("Task not found")
        }
        taskRepository.deleteById(id)
    }
}