package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.NotificationDTO
import org.example.colabtasksapi.mappers.NotificationMapper
import org.example.colabtasksapi.model.Notification
import org.example.colabtasksapi.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notificationMapper: NotificationMapper
) {

    @Transactional(readOnly = true)
    fun getAllNotifications(): List<NotificationDTO> {
        val notifications = notificationRepository.findAll().toList()
        if (notifications.isEmpty()) {
            throw NullPointerException("No notifications found")
        }
        return notificationMapper.toNotificationDTOList(notifications)
    }

    @Transactional(readOnly = true)
    fun getNotificationById(id: Long): NotificationDTO {
        val notification = notificationRepository.findById(id).orElseThrow { NullPointerException("Notification not found") }
        return notificationMapper.toNotificationDTO(notification)
    }

    @Transactional
    fun createNotification(notificationDTO: NotificationDTO): NotificationDTO {
        val notification = notificationMapper.toNotification(notificationDTO)
        val savedNotification = notificationRepository.save(notification)
        return notificationMapper.toNotificationDTO(savedNotification)
    }


    @Transactional
    fun deleteNotification(id: Long) {
        if (!notificationRepository.existsById(id)) {
            throw RuntimeException("Notification not found")
        }
        notificationRepository.deleteById(id)
    }
}