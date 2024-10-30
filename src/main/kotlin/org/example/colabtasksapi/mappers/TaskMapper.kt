package org.example.colabtasksapi.mappers

import org.example.colabtasksapi.dto.TaskDTO
import org.example.colabtasksapi.model.Task
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface TaskMapper {

    @Mappings(
        Mapping(source = "id", target = "idDto"),
        Mapping(source = "title", target = "titleDto"),
        Mapping(source = "description", target = "descriptionDto"),
        Mapping(source = "status", target = "statusDto"),
        Mapping(source = "priority", target = "priorityDto"),
        Mapping(source = "dueDate", target = "dueDateDto"),
        Mapping(source = "createdDate", target = "createdDateDto"),
        Mapping(source = "updatedDate", target = "updatedDateDto"),
        Mapping(source = "proyect", target = "proyectDto"),
        Mapping(source = "assignedUser", target = "assignedUserDto")
    )
    fun toTaskDTO(task: Task): TaskDTO

    fun toTaskDTOList(tasks: List<Task>): List<TaskDTO>

    @InheritInverseConfiguration
    fun toTask(taskDTO: TaskDTO): Task

    @InheritInverseConfiguration
    fun toTaskList(taskDTOs: List<TaskDTO>): List<Task>
}