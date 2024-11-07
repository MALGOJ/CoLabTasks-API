package org.example.colabtasksapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "proyects")
data class Proyect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    var createdDate: LocalDateTime,

    //Un proyecto puede tener muchas tareas asociadas a él. B
    @OneToMany(mappedBy = "proyect", cascade = [CascadeType.ALL])
    val tasks: List<Task> = mutableListOf(),

    //Un proyecto puede tener muchos usuarios asociados a él B
    @ManyToMany
    //tabla intermedia que se crea para la relación muchos a muchos
    @JoinTable(
        name = "user_proyect",
        joinColumns = [JoinColumn(name = "proyect_id")],
        inverseJoinColumns = [JoinColumn(name = "user_email")]
    )
    val users: List<User> = mutableListOf()
)

