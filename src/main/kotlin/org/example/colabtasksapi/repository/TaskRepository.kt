package org.example.colabtasksapi.repository

import org.example.colabtasksapi.model.Task
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface TaskRepository : CrudRepository<Task, Long> {
    fun findByTitle(title: String): List<Task>

    @Query("SELECT t FROM Task t WHERE t.assignedUser.email = :email")
    fun findAllByUserEmail(@Param("email") email: String): List<Task>
}