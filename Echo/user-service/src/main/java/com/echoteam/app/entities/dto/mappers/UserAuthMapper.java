package com.echoteam.app.entities.dto.mappers;

import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.dto.entityDTO.CreatedAuth;
import com.echoteam.app.entities.dto.entityDTO.UserAuthDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserAuthMapper {
    UserAuthMapper INSTANCE = Mappers.getMapper(UserAuthMapper.class);

    UserAuthDTO toDTOFromCreatedAuth(CreatedAuth createdAuth);
    UserAuth toUserAuth(UserAuthDTO userAuthDTO);

    UserAuthDTO toDTOFromUserAuth(UserAuth userAuth);
    CreatedAuth toCreatedAuth(UserAuthDTO userAuthDTO);

    List<UserAuthDTO> toDTOsFromCreated(List<CreatedAuth> createdAuths);
    List<UserAuth> toAuthsFromDTOs(List<UserAuthDTO> authDTOs);

    List<UserAuthDTO> toDTOsFromAuths(List<UserAuth> userAuths);
    List<CreatedAuth> toCreatedAuthsFromDTOs(List<UserAuthDTO> authDTOs);
}
