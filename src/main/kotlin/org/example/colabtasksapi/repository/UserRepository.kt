package org.example.colabtasksapi.repository

import org.example.colabtasksapi.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String> {

    fun findByEmail(email: String): User?
}