package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.NotificationDTO
import org.example.colabtasksapi.dto.TaskDTO
import org.example.colabtasksapi.mappers.NotificationMapper
import org.example.colabtasksapi.model.*
import org.example.colabtasksapi.repository.NotificationRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class NotificationServiceTest {

    @Mock
    private lateinit var notificationRepository: NotificationRepository

    @Mock
    private lateinit var notificationMapper: NotificationMapper

    @InjectMocks
    private lateinit var notificationService: NotificationService

    private lateinit var notification: Notification
    private lateinit var notificationDTO: NotificationDTO

    @BeforeEach
    fun setUp() {
        val task = Task(1L, "Test Task", "Description", TaskStatus.TODO, TaskPriority.HIGH, LocalDateTime.now(), LocalDateTime.now(), null)
        val taskDTO = TaskDTO(1L, "Test Task", "Description", TaskStatus.TODO, TaskPriority.HIGH, LocalDateTime.now())

        notification = Notification(1L, "Test Notification", NotificationType.REMINDER, LocalDateTime.now(), task)
        notificationDTO = NotificationDTO(1L, "Test Notification", NotificationType.DUE_DATE_ALERT, LocalDateTime.now(), taskDTO)
    }
    @Test
    fun getAllNotifications() {
        `when`(notificationRepository.findAll()).thenReturn(listOf(notification))
        `when`(notificationMapper.toNotificationDTOList(listOf(notification))).thenReturn(listOf(notificationDTO))

        val result = notificationService.getAllNotifications()

        assertEquals(1, result.size)
        assertEquals(notificationDTO, result[0])
        verify(notificationRepository, times(1)).findAll()
        verify(notificationMapper, times(1)).toNotificationDTOList(listOf(notification))
    }

    @Test
    fun getNotificationById() {
        val id = 1L
        `when`(notificationRepository.findById(id)).thenReturn(Optional.of(notification))
        `when`(notificationMapper.toNotificationDTO(notification)).thenReturn(notificationDTO)

        val result = notificationService.getNotificationById(id)

        assertEquals(notificationDTO, result)
        verify(notificationRepository, times(1)).findById(id)
        verify(notificationMapper, times(1)).toNotificationDTO(notification)
    }

    @Test
    fun createNotification() {
        `when`(notificationMapper.toNotification(notificationDTO)).thenReturn(notification)
        `when`(notificationRepository.save(notification)).thenReturn(notification)
        `when`(notificationMapper.toNotificationDTO(notification)).thenReturn(notificationDTO)

        val result = notificationService.createNotification(notificationDTO)

        assertEquals(notificationDTO, result)
        verify(notificationMapper, times(1)).toNotification(notificationDTO)
        verify(notificationRepository, times(1)).save(notification)
        verify(notificationMapper, times(1)).toNotificationDTO(notification)
    }

    @Test
    fun deleteNotification() {
        val id = 1L
        `when`(notificationRepository.existsById(id)).thenReturn(true)

        notificationService.deleteNotification(id)

        verify(notificationRepository, times(1)).existsById(id)
        verify(notificationRepository, times(1)).deleteById(id)
    }
}