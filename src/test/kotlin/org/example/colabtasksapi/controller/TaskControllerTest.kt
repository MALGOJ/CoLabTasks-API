package org.example.colabtasksapi.controller

import org.example.colabtasksapi.dto.TaskDTO
import org.example.colabtasksapi.service.TaskService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.colabtasksapi.dto.TaskResponseDTO
import org.example.colabtasksapi.model.TaskPriority
import org.example.colabtasksapi.model.TaskStatus
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class TaskControllerTest {

    @Mock
    private lateinit var taskService: TaskService

    @Mock
    private lateinit var authentication: Authentication

    @InjectMocks
    private lateinit var taskController: TaskController

    private lateinit var mockMvc: MockMvc

    lateinit var taskDTO: TaskDTO

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build()
    }

    @Test
    fun getAllTasks() {
        `when`(authentication.isAuthenticated).thenReturn(true)
        `when`(authentication.name).thenReturn("test@example.com")
        `when`(taskService.getAllTasksByUser("test@example.com")).thenReturn(listOf())

        mockMvc.perform(get("/api/tasks").principal(authentication))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray)

        verify(taskService, times(1)).getAllTasksByUser("test@example.com")
    }

    @Test
    fun getTaskById() {
        `when`(authentication.isAuthenticated).thenReturn(true)
        `when`(taskService.getTaskById(1L)).thenReturn(TaskResponseDTO(
            id = 1L,
            title = "Test Task",
            description = "Description",
            status = TaskStatus.IN_PROGRESS, // Replace with an appropriate TaskStatus value
            priority = TaskPriority.HIGH, // Replace with an appropriate TaskPriority value
            dueDate = LocalDateTime.now().plusDays(1),
            createdDate = LocalDateTime.now(),
            updatedDate = null,
            proyect = null,
            assignedUser = null
        ))

        mockMvc.perform(get("/api/tasks/getTaskById/1").principal(authentication))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("Test Task"))

        verify(taskService, times(1)).getTaskById(1L)
    }

    @Test
    fun deleteTask() {
        `when`(authentication.isAuthenticated).thenReturn(true)

        mockMvc.perform(delete("/api/tasks/deleteTask/1").principal(authentication))
            .andExpect(status().isNoContent)

        verify(taskService, times(1)).deleteTask(1L)
    }
}