package org.example.colabtasksapi.mappers

import org.example.colabtasksapi.dto.NotificationDTO
import org.example.colabtasksapi.model.Notification
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface NotificationMapper {

    @Mappings(
        Mapping(source = "id", target = "idDto"),
        Mapping(source = "message", target = "messageDto"),
        Mapping(source = "type", target = "typeDto"),
        Mapping(source = "sendDate", target = "sendDateDto"),
        Mapping(source = "task", target = "taskDto")
    )
    fun toNotificationDTO(notification: Notification): NotificationDTO

    fun toNotificationDTOList(notifications: List<Notification>): List<NotificationDTO>

    @InheritInverseConfiguration
    fun toNotification(notificationDTO: NotificationDTO): Notification

    @InheritInverseConfiguration
    fun toNotificationList(notificationDTOs: List<NotificationDTO>): List<Notification>
}