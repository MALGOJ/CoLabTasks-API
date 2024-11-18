package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.ReminderDTO
import org.example.colabtasksapi.mappers.ReminderMapper
import org.example.colabtasksapi.model.Reminder
import org.example.colabtasksapi.repository.ReminderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for managing reminders.
 *
 * @property reminderRepository Repository for reminder data.
 * @property reminderMapper Mapper for converting between reminder entities and DTOs.
 */
@Service
class ReminderService(
    private val reminderRepository: ReminderRepository,
    private val reminderMapper: ReminderMapper
) {

    /**
     * Retrieves all reminders.
     *
     * @return A list of ReminderDTO objects representing all reminders.
     * @throws NullPointerException if no reminders are found.
     */
    @Transactional(readOnly = true)
    fun getAllReminders(): List<ReminderDTO> {
        val reminders = reminderRepository.findAll().toList()
        if (reminders.isEmpty()) {
            throw NullPointerException("No reminders found")
        }
        return reminderMapper.toReminderDTOList(reminders)
    }

    /**
     * Retrieves a reminder by its ID.
     *
     * @param id The ID of the reminder.
     * @return A ReminderDTO object representing the reminder.
     * @throws NullPointerException if the reminder is not found.
     */
    @Transactional(readOnly = true)
    fun getReminderById(id: Long): ReminderDTO {
        val reminder = reminderRepository.findById(id).orElseThrow { NullPointerException("Reminder not found") }
        return reminderMapper.toReminderDTO(reminder)
    }

    /**
     * Creates a new reminder.
     *
     * @param reminderDTO The data transfer object containing reminder details.
     * @return A ReminderDTO object representing the created reminder.
     */
    @Transactional
    fun createReminder(reminderDTO: ReminderDTO): ReminderDTO {
        val reminder = reminderMapper.toReminder(reminderDTO)
        val savedReminder = reminderRepository.save(reminder)
        return reminderMapper.toReminderDTO(savedReminder)
    }

    /**
     * Deletes a reminder by its ID.
     *
     * @param id The ID of the reminder to be deleted.
     * @throws RuntimeException if the reminder is not found.
     */
    @Transactional
    fun deleteReminder(id: Long) {
        if (!reminderRepository.existsById(id)) {
            throw RuntimeException("Reminder not found")
        }
        reminderRepository.deleteById(id)
    }
}