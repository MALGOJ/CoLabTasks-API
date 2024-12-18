package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val createDate: LocalDate,

    @Column(nullable = false)
    val locked: Boolean = false,

    @Column(nullable = false)
    val disabled: Boolean = false,

    @ManyToMany(mappedBy = "users")
    val proyects: List<Proyect> = mutableListOf()
)