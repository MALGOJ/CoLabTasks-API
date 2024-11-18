package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.ProjectResponseDTO
import org.example.colabtasksapi.dto.ProyectDTO
import org.example.colabtasksapi.mappers.ProyectMapper
import org.example.colabtasksapi.mappers.TaskMapper
import org.example.colabtasksapi.mappers.UserMapper
import org.example.colabtasksapi.model.Proyect
import org.example.colabtasksapi.model.User
import org.example.colabtasksapi.repository.ProyectRepository
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
class ProyectServiceTest {

    @Mock
    private lateinit var proyectRepository: ProyectRepository

    @Mock
    private lateinit var proyectMapper: ProyectMapper

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var taskMapper: TaskMapper

    @Mock
    private lateinit var userMapper: UserMapper

    @InjectMocks
    private lateinit var proyectService: ProyectService

    private lateinit var proyect: Proyect
    private lateinit var proyectDTO: ProyectDTO
    private lateinit var projectResponseDTO: ProjectResponseDTO

    @BeforeEach
    fun setUp() {
        proyect = Proyect(1L, "Test Project", "Description", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), mutableListOf(),mutableListOf())
        proyectDTO = ProyectDTO(1L, "name project", "description project", LocalDateTime.now(), LocalDateTime.now(),LocalDateTime.now(), mutableListOf(), mutableListOf())
        projectResponseDTO = ProjectResponseDTO("project one", "Test Project", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), mutableListOf(), mutableListOf())
    }

    @Test
    fun getAllProjectsByUser() {
        val email = "test@example.com"
        `when`(proyectRepository.findAllByUserEmail(email)).thenReturn(listOf(proyect))
        `when`(proyectMapper.toProjectResponseDTO(proyect)).thenReturn(projectResponseDTO)

        val result = proyectService.getAllProjectsByUser(email)

        assertEquals(1, result.size)
        assertEquals(projectResponseDTO, result[0])
        verify(proyectRepository, times(1)).findAllByUserEmail(email)
        verify(proyectMapper, times(1)).toProjectResponseDTO(proyect)
    }

    @Test
    fun getProjectById() {
        val id = 1L
        `when`(proyectRepository.findById(id)).thenReturn(Optional.of(proyect))
        `when`(proyectMapper.toProyectDTO(proyect)).thenReturn(proyectDTO)

        val result = proyectService.getProjectById(id)

        assertEquals(proyectDTO, result)
        verify(proyectRepository, times(1)).findById(id)
        verify(proyectMapper, times(1)).toProyectDTO(proyect)
    }

    @Test
    fun createProject() {
        val email = "test@example.com"
        val user = User(
            email = "test@example.com",
            password = "password",
            name = "Test User",
            createDate = LocalDate.now(),
            locked = true,
            disabled = false,
            proyects = mutableListOf()
        )
        `when`(userRepository.findByEmail(email)).thenReturn(user)
        `when`(proyectMapper.toProyect(proyectDTO)).thenReturn(proyect)

        proyectService.createProject(proyectDTO, email)

        verify(userRepository, times(1)).findByEmail(email)
        verify(proyectMapper, times(1)).toProyect(proyectDTO)
        verify(proyectRepository, times(1)).save(proyect)
    }

    @Test
    fun updateProject() {
        val id = 1L
        `when`(proyectRepository.findById(id)).thenReturn(Optional.of(proyect))
        `when`(proyectRepository.save(proyect)).thenReturn(proyect)
        `when`(proyectMapper.toProjectResponseDTO(proyect)).thenReturn(projectResponseDTO)

        val result = proyectService.updateProject(id, proyectDTO)

        assertEquals(projectResponseDTO, result)
        verify(proyectRepository, times(1)).findById(id)
        verify(proyectRepository, times(1)).save(proyect)
        verify(proyectMapper, times(1)).toProjectResponseDTO(proyect)
    }

    @Test
    fun deleteProject() {
        val id = 1L
        `when`(proyectRepository.existsById(id)).thenReturn(true)

        proyectService.deleteProject(id)

        verify(proyectRepository, times(1)).existsById(id)
        verify(proyectRepository, times(1)).deleteById(id)
    }
}