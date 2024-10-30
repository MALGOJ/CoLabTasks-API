package org.example.colabtasksapi.mappers

import org.example.colabtasksapi.dto.ProyectDTO
import org.example.colabtasksapi.model.Proyect
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ProyectMapper {

    @Mappings(
        Mapping(source = "id", target = "idDto"),
        Mapping(source = "name", target = "nameDto"),
        Mapping(source = "description", target = "descriptionDto"),
        Mapping(source = "startDate", target = "startDateDto"),
        Mapping(source = "endDate", target = "endDateDto"),
        Mapping(source = "createdDate", target = "createdDateDto"),
        Mapping(source = "tasks", target = "tasksDto"),
        Mapping(source = "users", target = "usersDto")
    )
    fun toProyectDTO(proyect: Proyect): ProyectDTO

    fun toProyectDTOList(proyects: List<Proyect>): List<ProyectDTO>

    @InheritInverseConfiguration
    fun toProyect(proyectDTO: ProyectDTO): Proyect

    @InheritInverseConfiguration
    fun toProyectList(proyectDTOs: List<ProyectDTO>): List<Proyect>
}