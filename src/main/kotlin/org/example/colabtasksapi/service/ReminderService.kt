package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.ReminderDTO
import org.example.colabtasksapi.mappers.ReminderMapper
import org.example.colabtasksapi.model.Reminder
import org.example.colabtasksapi.repository.ReminderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReminderService(
    private val reminderRepository: ReminderRepository,
    private val reminderMapper: ReminderMapper
) {

    @Transactional(readOnly = true)
    fun getAllReminders(): List<ReminderDTO> {
        val reminders = reminderRepository.findAll().toList()
        if (reminders.isEmpty()) {
            throw NullPointerException("No reminders found")
        }
        return reminderMapper.toReminderDTOList(reminders)
    }

    @Transactional(readOnly = true)
    fun getReminderById(id: Long): ReminderDTO {
        val reminder = reminderRepository.findById(id).orElseThrow { NullPointerException("Reminder not found") }
        return reminderMapper.toReminderDTO(reminder)
    }

    @Transactional
    fun createReminder(reminderDTO: ReminderDTO): ReminderDTO {
        val reminder = reminderMapper.toReminder(reminderDTO)
        val savedReminder = reminderRepository.save(reminder)
        return reminderMapper.toReminderDTO(savedReminder)
    }


    @Transactional
    fun deleteReminder(id: Long) {
        if (!reminderRepository.existsById(id)) {
            throw RuntimeException("Reminder not found")
        }
        reminderRepository.deleteById(id)
    }
}