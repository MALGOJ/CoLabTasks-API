package org.example.colabtasksapi.service

import org.example.colabtasksapi.controller.TaskController
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

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val taskMapper: TaskMapper,
    private val userRepository: UserRepository,
) {

    @Transactional(readOnly = true)
    fun getAllTasksByUser(email: String): List<TaskResponseDTO> {
        val tasks = taskRepository.findAllByUserEmail(email)
        return tasks.map { taskMapper.toTaskResponseDTO(it) }
    }

    @Transactional(readOnly = true)
    fun getTaskById(id: Long): TaskResponseDTO {
        val task = taskRepository.findById(id).orElseThrow { NullPointerException("Task not found") }
        return taskMapper.toTaskResponseDTO(task)
    }

    @Transactional //el evento se ejecuta dentro de una trasacción, si ocurre una excepcion se hace un rollback
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

    @Transactional
    fun deleteTask(id: Long) {
        if (!taskRepository.existsById(id)) {
            throw RuntimeException("Task not found")
        }
        taskRepository.deleteById(id)
    }
}