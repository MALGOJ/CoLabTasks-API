package org.example.colabtasksapi.repository

import org.example.colabtasksapi.model.Proyect
import org.springframework.data.repository.CrudRepository

interface ProyectRepository : CrudRepository<Proyect, Long>