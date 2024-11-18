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
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var startDate: LocalDateTime,

    @Column(nullable = false)
    var endDate: LocalDateTime,

    @Column(nullable = false)
    var createdDate: LocalDateTime,

    //Un proyecto puede tener muchas tareas asociadas a él. B
    @OneToMany(mappedBy = "proyect", cascade = [CascadeType.ALL])
    var tasks: MutableList<Task> = mutableListOf(),

    //Un proyecto puede tener muchos usuarios asociados a él B
    @ManyToMany
    //tabla intermedia que se crea para la relación muchos a muchos
    @JoinTable(
        name = "user_proyect",
        joinColumns = [JoinColumn(name = "proyect_id")],
        inverseJoinColumns = [JoinColumn(name = "user_email")]
    )
    var users: MutableList<User> = mutableListOf()
)

