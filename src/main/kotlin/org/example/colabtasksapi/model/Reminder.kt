package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reminders")
data class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Enumerated(EnumType.STRING)
    val frequency: ReminderFrequency,

    val startDate: LocalDateTime,
    val endDate: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    val task: Task,

    @ManyToOne
    @JoinColumn(name = "user_email", nullable = false)
    val user: User
    )
