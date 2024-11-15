package org.example.colabtasksapi.repository

import org.example.colabtasksapi.model.Proyect
import org.example.colabtasksapi.model.Task
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ProyectRepository : CrudRepository<Proyect, Long>{

    @Query("SELECT P FROM Proyect P " +
            "JOIN P.users u " +
            "WHERE u.email = :email")
    fun findAllByUserEmail(@Param("email") email: String): List<Proyect>

}