package org.example.colabtasksapi.model

enum class TaskStatus {
    TODO, IN_PROGRESS, COMPLETED
}

enum class TaskPriority {
    HIGH, MEDIUM, LOW
}

enum class NotificationType {
    REMINDER, DUE_DATE_ALERT
}

enum class ReminderFrequency {
    DAILY, WEEKLY, MONTHLY
}