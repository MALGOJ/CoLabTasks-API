package org.example.colabtasksapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class CoLabTasksApiApplication

fun main(args: Array<String>) {
    runApplication<CoLabTasksApiApplication>(*args)
}
