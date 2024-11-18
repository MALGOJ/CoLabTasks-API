package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    var status: TaskStatus,

    @Enumerated(EnumType.STRING)
    var priority: TaskPriority,

    var dueDate: LocalDateTime,
    var createdDate: LocalDateTime,
    var updatedDate: LocalDateTime? = null,

    //Muchas tareas pueden estar asociadas a un único proyecto B
    @ManyToOne
    @JoinColumn(name = "proyect_id")
    var proyect: Proyect? = null,

    //Muchas tareas pueden estar asignadas a un único usuario B
    @ManyToOne
    @JoinColumn(name = "user_email")
    var assignedUser: User? = null,
)