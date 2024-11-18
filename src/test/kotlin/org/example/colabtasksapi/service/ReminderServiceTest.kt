package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.ReminderDTO
import org.example.colabtasksapi.dto.TaskDTO
import org.example.colabtasksapi.dto.UserDTO
import org.example.colabtasksapi.mappers.ReminderMapper
import org.example.colabtasksapi.model.*
import org.example.colabtasksapi.repository.ReminderRepository
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
class ReminderServiceTest {

    @Mock
    private lateinit var reminderRepository: ReminderRepository

    @Mock
    private lateinit var reminderMapper: ReminderMapper

    @InjectMocks
    private lateinit var reminderService: ReminderService

    private lateinit var reminder: Reminder
    private lateinit var reminderDTO: ReminderDTO

    @BeforeEach
    fun setUp() {
        val task = Task(1L, "Test Task", "Description", TaskStatus.TODO, TaskPriority.HIGH, LocalDateTime.now(), LocalDateTime.now(), null)
        val taskDTO = TaskDTO(1L, "Test Task", "Description", TaskStatus.TODO, TaskPriority.HIGH, LocalDateTime.now())
        val user = User("test@example.com", "password", "Test User", LocalDate.now(), true, false, mutableListOf())
        val userDTO = UserDTO("test@example.com", "Test User", LocalDate.now(), "passs", false,false, mutableListOf())

        reminder = Reminder(1L, ReminderFrequency.WEEKLY, LocalDateTime.now(), LocalDateTime.now(), task, user)
        reminderDTO = ReminderDTO(1L, ReminderFrequency.WEEKLY, LocalDateTime.now(), LocalDateTime.now(), taskDTO, userDTO)
    }

    @Test
    fun getAllReminders() {
        `when`(reminderRepository.findAll()).thenReturn(listOf(reminder))
        `when`(reminderMapper.toReminderDTOList(listOf(reminder))).thenReturn(listOf(reminderDTO))

        val result = reminderService.getAllReminders()

        assertEquals(1, result.size)
        assertEquals(reminderDTO, result[0])
        verify(reminderRepository, times(1)).findAll()
        verify(reminderMapper, times(1)).toReminderDTOList(listOf(reminder))
    }

    @Test
    fun getReminderById() {
        val id = 1L
        `when`(reminderRepository.findById(id)).thenReturn(Optional.of(reminder))
        `when`(reminderMapper.toReminderDTO(reminder)).thenReturn(reminderDTO)

        val result = reminderService.getReminderById(id)

        assertEquals(reminderDTO, result)
        verify(reminderRepository, times(1)).findById(id)
        verify(reminderMapper, times(1)).toReminderDTO(reminder)
    }

    @Test
    fun createReminder() {
        `when`(reminderMapper.toReminder(reminderDTO)).thenReturn(reminder)
        `when`(reminderRepository.save(reminder)).thenReturn(reminder)
        `when`(reminderMapper.toReminderDTO(reminder)).thenReturn(reminderDTO)

        val result = reminderService.createReminder(reminderDTO)

        assertEquals(reminderDTO, result)
        verify(reminderMapper, times(1)).toReminder(reminderDTO)
        verify(reminderRepository, times(1)).save(reminder)
        verify(reminderMapper, times(1)).toReminderDTO(reminder)
    }

    @Test
    fun deleteReminder() {
        val id = 1L
        `when`(reminderRepository.existsById(id)).thenReturn(true)

        reminderService.deleteReminder(id)

        verify(reminderRepository, times(1)).existsById(id)
        verify(reminderRepository, times(1)).deleteById(id)
    }
}