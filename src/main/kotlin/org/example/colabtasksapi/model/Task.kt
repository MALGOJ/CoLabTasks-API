package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val title: String,
    val description: String,

    @Enumerated(EnumType.STRING)
    val status: TaskStatus,

    @Enumerated(EnumType.STRING)
    val priority: TaskPriority,

    val dueDate: LocalDateTime,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,


)
