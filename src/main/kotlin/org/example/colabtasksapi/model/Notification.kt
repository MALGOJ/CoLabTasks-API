package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val message: String,

    @Enumerated(EnumType.STRING)
    val type: NotificationType,

    val sendDate: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    val task: Task
)
/* Definir una caducidad o eliminación periódica:
    Para no acumular notificaciones innecesarias,
    se puede implementar un sistema que borre las antiguas
    o irrelevantes pasado un tiempo.
 */