package org.example.colabtasksapi.repository

import org.example.colabtasksapi.model.Reminder
import org.springframework.data.repository.CrudRepository

interface ReminderRepository : CrudRepository<Reminder, Long>