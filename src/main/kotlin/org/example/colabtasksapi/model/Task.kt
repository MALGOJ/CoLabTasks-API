package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String,

    @Enumerated(EnumType.STRING)
    val status: TaskStatus,

    @Enumerated(EnumType.STRING)
    val priority: TaskPriority,

    val dueDate: LocalDateTime,
    val createdDate: LocalDateTime,
    val updatedDate: LocalDateTime,

    //Muchas tareas pueden estar asociadas a un único proyecto B
    @ManyToOne
    @JoinColumn(name = "proyect_id", nullable = false)
    val proyect: Proyect,

    //Muchas tareas pueden estar asignadas a un único usuario B
    @ManyToOne
    @JoinColumn(name = "user_email")
    val assignedUser: User? = null,
)