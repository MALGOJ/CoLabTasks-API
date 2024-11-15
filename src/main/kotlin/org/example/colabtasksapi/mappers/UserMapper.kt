package org.example.colabtasksapi.mappers

import org.example.colabtasksapi.dto.UserDTO
import org.example.colabtasksapi.dto.UserEmailDto
import org.example.colabtasksapi.model.User
import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {

    @Mappings(
        Mapping(source = "email", target = "emailDto"),
        Mapping(source = "name", target = "nameDto"),
        Mapping(source = "createDate", target = "createDateDto"),
        Mapping(source = "password", target = "passwordDto"),
        Mapping(source = "locked", target = "lockedDto"),
        Mapping(source = "disabled", target = "disabledDto"),
        Mapping(source = "proyects", target = "proyectsDto")
    )
    fun toUserDTO(user: User): UserDTO

    fun toUserDTOList(users: List<User>): List<UserDTO>

    @Mapping(source = "email", target = "email")
    fun toUserEmailDto(user: User): UserEmailDto

    @InheritInverseConfiguration
    fun toUserEmail(userEmailDTO: UserEmailDto): User

    @InheritInverseConfiguration
    fun toUser(userDTO: UserDTO): User

    @InheritInverseConfiguration
    fun toUserList(userDTOs: List<UserDTO>): List<User>
}