package org.example.colabtasksapi.mappers

import org.example.colabtasksapi.dto.TaskDTO
import org.example.colabtasksapi.dto.TaskResponseDTO
import org.example.colabtasksapi.dto.AssignedUserResponseDTO
import org.example.colabtasksapi.model.Task
import org.example.colabtasksapi.model.User
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
interface TaskMapper {

    @Mapping(source = "id", target = "idDto")
    @Mapping(source = "title", target = "titleDto")
    @Mapping(source = "description", target = "descriptionDto")
    @Mapping(source = "status", target = "statusDto")
    @Mapping(source = "priority", target = "priorityDto")
    @Mapping(source = "dueDate", target = "dueDateDto")
    @Mapping(source = "createdDate", target = "createdDateDto")
    @Mapping(source = "updatedDate", target = "updatedDateDto")
    @Mapping(source = "proyect", target = "proyectDto")
    @Mapping(source = "assignedUser", target = "assignedUserDto")
    fun toTaskDTO(task: Task): TaskDTO

    @Mapping(source = "idDto", target = "id")
    @Mapping(source = "titleDto", target = "title")
    @Mapping(source = "descriptionDto", target = "description")
    @Mapping(source = "statusDto", target = "status")
    @Mapping(source = "priorityDto", target = "priority")
    @Mapping(source = "dueDateDto", target = "dueDate")
    @Mapping(source = "createdDateDto", target = "createdDate")
    @Mapping(source = "updatedDateDto", target = "updatedDate")
    @Mapping(source = "proyectDto", target = "proyect")
    @Mapping(source = "assignedUserDto", target = "assignedUser")
    fun toTask(taskDTO: TaskDTO): Task

    fun toTaskDTOList(tasks: List<Task>): List<TaskDTO>

    fun toTaskList(taskDTOs: List<TaskDTO>): List<Task>

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "dueDate", target = "dueDate")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "updatedDate", target = "updatedDate")
    @Mapping(source = "proyect", target = "proyect")
    @Mapping(source = "assignedUser", target = "assignedUser")
    fun toTaskResponseDTO(task: Task): TaskResponseDTO

    @Mapping(source = "email", target = "email")
    fun toAssignedUserResponseDTO(user: User): AssignedUserResponseDTO
}