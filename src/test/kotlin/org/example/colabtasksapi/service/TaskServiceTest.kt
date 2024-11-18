package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.AssignedUserResponseDTO
import org.example.colabtasksapi.dto.TaskDTO
import org.example.colabtasksapi.dto.TaskResponseDTO
import org.example.colabtasksapi.dto.UserDTO
import org.example.colabtasksapi.mappers.TaskMapper
import org.example.colabtasksapi.model.Task
import org.example.colabtasksapi.model.TaskPriority
import org.example.colabtasksapi.model.TaskStatus
import org.example.colabtasksapi.model.User
import org.example.colabtasksapi.repository.TaskRepository
import org.example.colabtasksapi.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class TaskServiceTest {

    @Mock
    private lateinit var taskRepository: TaskRepository

    @Mock
    private lateinit var taskMapper: TaskMapper

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var taskService: TaskService

    private lateinit var task: Task
    private lateinit var taskDTO: TaskDTO
    private lateinit var taskResponseDTO: TaskResponseDTO

    @BeforeEach
    fun setUp() {
        task = Task(1L, "Test Task", "Description", TaskStatus.TODO, TaskPriority.HIGH , LocalDateTime.now(), LocalDateTime.now(), null)
        taskDTO = TaskDTO(1L, "Description", "OPEN", TaskStatus.TODO, TaskPriority.HIGH , LocalDateTime.now())
        taskResponseDTO = TaskResponseDTO(
            1L,
            "Test Task",
            "Description",
            TaskStatus.TODO,
            TaskPriority.HIGH ,
            LocalDateTime.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            null,
            AssignedUserResponseDTO("test@example.com")
        )
    }

    @Test
    fun getAllTasksByUser() {
        val email = "test@example.com"
        `when`(taskRepository.findAllByUserEmail(email)).thenReturn(listOf(task))
        `when`(taskMapper.toTaskResponseDTO(task)).thenReturn(taskResponseDTO)

        val result = taskService.getAllTasksByUser(email)

        assertEquals(1, result.size)
        assertEquals(taskResponseDTO, result[0])
        verify(taskRepository, times(1)).findAllByUserEmail(email)
        verify(taskMapper, times(1)).toTaskResponseDTO(task)
    }

    @Test
    fun getTaskById() {
        val id = 1L
        `when`(taskRepository.findById(id)).thenReturn(Optional.of(task))
        `when`(taskMapper.toTaskResponseDTO(task)).thenReturn(taskResponseDTO)

        val result = taskService.getTaskById(id)

        assertEquals(taskResponseDTO, result)
        verify(taskRepository, times(1)).findById(id)
        verify(taskMapper, times(1)).toTaskResponseDTO(task)
    }

    @Test
    fun createTask() {
        val email = "test@example.com"
        `when`(userRepository.findByEmail(email)).thenReturn(mock(User::class.java))
        `when`(taskMapper.toTask(taskDTO)).thenReturn(task)
        `when`(taskRepository.save(task)).thenReturn(task)
        `when`(taskMapper.toTaskResponseDTO(task)).thenReturn(taskResponseDTO)

        val result = taskService.createTask(taskDTO, email)

        assertEquals(taskResponseDTO, result)
        verify(userRepository, times(1)).findByEmail(email)
        verify(taskMapper, times(1)).toTask(taskDTO)
        verify(taskRepository, times(1)).save(task)
        verify(taskMapper, times(1)).toTaskResponseDTO(task)
    }

    @Test
    fun updateTask() {
        val id = 1L
        `when`(taskRepository.findById(id)).thenReturn(Optional.of(task))
        `when`(taskRepository.save(task)).thenReturn(task)
        `when`(taskMapper.toTaskResponseDTO(task)).thenReturn(taskResponseDTO)

        val result = taskService.updateTask(id, taskDTO)

        assertEquals(taskResponseDTO, result)
        verify(taskRepository, times(1)).findById(id)
        verify(taskRepository, times(1)).save(task)
        verify(taskMapper, times(1)).toTaskResponseDTO(task)
    }

    @Test
    fun deleteTask() {
        val id = 1L
        `when`(taskRepository.existsById(id)).thenReturn(true)

        taskService.deleteTask(id)

        verify(taskRepository, times(1)).existsById(id)
        verify(taskRepository, times(1)).deleteById(id)
    }
}