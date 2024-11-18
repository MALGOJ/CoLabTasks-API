package org.example.colabtasksapi.repository

import org.example.colabtasksapi.model.Notification
import org.springframework.data.repository.CrudRepository

interface NotificationRepository : CrudRepository<Notification, Long>