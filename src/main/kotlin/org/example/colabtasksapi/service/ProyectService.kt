package org.example.colabtasksapi.service

import org.example.colabtasksapi.dto.ProyectDTO
import org.example.colabtasksapi.mappers.ProyectMapper
import org.example.colabtasksapi.model.Proyect
import org.example.colabtasksapi.repository.ProyectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProyectService(
    private val proyectRepository: ProyectRepository,
    private val proyectMapper: ProyectMapper
) {

    @Transactional(readOnly = true)
    fun getAllProyects(): List<ProyectDTO> {
        val proyects = proyectRepository.findAll().toList()
        if (proyects.isEmpty()) {
            throw NullPointerException("No proyects found")
        }
        return proyectMapper.toProyectDTOList(proyects)
    }

    @Transactional(readOnly = true)
    fun getProyectById(id: Long): ProyectDTO {
        val proyect = proyectRepository.findById(id).orElseThrow { NullPointerException("Proyect not found") }
        return proyectMapper.toProyectDTO(proyect)
    }

    @Transactional
    fun createProyect(proyectDTO: ProyectDTO): ProyectDTO {
        val proyect = proyectMapper.toProyect(proyectDTO).apply {
            createdDate = proyectDTO.createdDateDto ?: LocalDateTime.now()
        }
        val savedProyect = proyectRepository.save(proyect)
        return proyectMapper.toProyectDTO(savedProyect)
    }

    @Transactional
    fun updateProyect(id: Long, updatedProyectDTO: ProyectDTO): ProyectDTO {
        val existingProyect = proyectRepository.findById(id).orElseThrow { RuntimeException("Proyect not found") }

        val updatedProyect = proyectMapper.toProyect(updatedProyectDTO).apply {
            this.createdDate = existingProyect.createdDate
        }

        val savedProyect = proyectRepository.save(updatedProyect)
        return proyectMapper.toProyectDTO(savedProyect)
    }

    @Transactional
    fun deleteProyect(id: Long) {
        if (!proyectRepository.existsById(id)) {
            throw RuntimeException("Proyect not found")
        }
        proyectRepository.deleteById(id)
    }
}