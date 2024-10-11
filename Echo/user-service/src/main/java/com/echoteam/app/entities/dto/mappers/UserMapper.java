package com.echoteam.app.entities.dto.mappers;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.entityDTO.CreatedUser;
import com.echoteam.app.entities.dto.entityDTO.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Qualifier;
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
    User toUserFromDTO(UserDTO userDTO);

    UserDTO toDTOFromUser(User user);
    CreatedUser toCreatedUser(UserDTO userDTO);

    List<UserDTO> toDTOsFromCreatedUsers(List<CreatedUser> createdUsers);
    List<User> toUsers(List<UserDTO> userDTOs);

    List<UserDTO> toDTOs(List<User> users);
    List<CreatedUser> toCreatedUsersFromDTOs(List<UserDTO> userDTOs);

}
