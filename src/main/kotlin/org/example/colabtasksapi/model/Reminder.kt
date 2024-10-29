package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reminders")
data class Reminder(
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Enumerated(EnumType.STRING)
    val frequency: ReminderFrequency,

    val startDate: LocalDateTime,

    val endDate: LocalDateTime,
)
