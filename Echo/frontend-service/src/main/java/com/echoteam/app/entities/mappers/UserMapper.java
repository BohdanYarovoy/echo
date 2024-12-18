package com.echoteam.app.entities.mappers;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.changedDTO.ChangedUser;
import com.echoteam.app.entities.dto.createdDTO.CreatedUser;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
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
    ChangedUser toChangedFromDTO(UserDTO userDTO);

    List<UserDTO> toDTOs(List<User> users);
}
