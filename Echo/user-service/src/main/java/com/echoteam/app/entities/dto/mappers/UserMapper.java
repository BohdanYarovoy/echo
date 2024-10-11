package com.echoteam.app.entities.dto.mappers;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.entityDTO.UserDTO;
import com.echoteam.app.entities.dto.entityDTO.changed.ChangedUser;
import com.echoteam.app.entities.dto.entityDTO.created.CreatedUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {
        UserDetailMapper.class,
        UserAuthMapper.class,
        UserRoleMapper.class,
})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTOFromCreatedUser(CreatedUser createdUser);
    UserDTO toDTOFromChangedUser(ChangedUser changedUser);
    User toUserFromDTO(UserDTO userDTO);

    UserDTO toDTOFromUser(User user);

    List<UserDTO> toDTOs(List<User> users);
}
