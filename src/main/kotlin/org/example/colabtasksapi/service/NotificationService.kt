package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.NotificationDTO
import org.example.colabtasksapi.mappers.NotificationMapper
import org.example.colabtasksapi.model.Notification
import org.example.colabtasksapi.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for managing notifications.
 *
 * @property notificationRepository Repository for notification data.
 * @property notificationMapper Mapper for converting between notification entities and DTOs.
 */
@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notificationMapper: NotificationMapper
) {

    /**
     * Retrieves all notifications.
     *
     * @return A list of NotificationDTO objects representing all notifications.
     * @throws NullPointerException if no notifications are found.
     */
    @Transactional(readOnly = true)
    fun getAllNotifications(): List<NotificationDTO> {
        val notifications = notificationRepository.findAll().toList()
        if (notifications.isEmpty()) {
            throw NullPointerException("No notifications found")
        }
        return notificationMapper.toNotificationDTOList(notifications)
    }

    /**
     * Retrieves a notification by its ID.
     *
     * @param id The ID of the notification.
     * @return A NotificationDTO object representing the notification.
     * @throws NullPointerException if the notification is not found.
     */
    @Transactional(readOnly = true)
    fun getNotificationById(id: Long): NotificationDTO {
        val notification = notificationRepository.findById(id).orElseThrow { NullPointerException("Notification not found") }
        return notificationMapper.toNotificationDTO(notification)
    }

    /**
     * Creates a new notification.
     *
     * @param notificationDTO The data transfer object containing notification details.
     * @return A NotificationDTO object representing the created notification.
     */
    @Transactional
    fun createNotification(notificationDTO: NotificationDTO): NotificationDTO {
        val notification = notificationMapper.toNotification(notificationDTO)
        val savedNotification = notificationRepository.save(notification)
        return notificationMapper.toNotificationDTO(savedNotification)
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param id The ID of the notification to be deleted.
     * @throws RuntimeException if the notification is not found.
     */
    @Transactional
    fun deleteNotification(id: Long) {
        if (!notificationRepository.existsById(id)) {
            throw RuntimeException("Notification not found")
        }
        notificationRepository.deleteById(id)
    }
}