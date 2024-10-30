package org.example.colabtasksapi.mappers

import org.example.colabtasksapi.dto.ReminderDTO
import org.example.colabtasksapi.model.Reminder
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ReminderMapper {

    @Mappings(
        Mapping(source = "id", target = "idDto"),
        Mapping(source = "frequency", target = "frequencyDto"),
        Mapping(source = "startDate", target = "startDateDto"),
        Mapping(source = "endDate", target = "endDateDto"),
        Mapping(source = "task", target = "taskDto"),
        Mapping(source = "user", target = "userDto")
    )
    fun toReminderDTO(reminder: Reminder): ReminderDTO

    fun toReminderDTOList(reminders: List<Reminder>): List<ReminderDTO>

    @InheritInverseConfiguration
    fun toReminder(reminderDTO: ReminderDTO): Reminder

    @InheritInverseConfiguration
    fun toReminderList(reminderDTOs: List<ReminderDTO>): List<Reminder>
}