package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "proyects")
data class Proyect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val startDate: LocalDateTime,

    @Column(nullable = false)
    val endDate: LocalDateTime,

    @Column(nullable = false)
    val createdDate: LocalDateTime,
)
