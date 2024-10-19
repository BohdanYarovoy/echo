package com.echoteam.app.entities.mappers;

import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.dto.changedDTO.ChangedUserAuth;
import com.echoteam.app.entities.dto.createdDTO.CreatedAuth;
import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserAuthMapper {
    UserAuthMapper INSTANCE = Mappers.getMapper(UserAuthMapper.class);

    UserAuthDTO toDTOFromCreatedAuth(CreatedAuth createdAuth);
    UserAuthDTO toDTOFromChangedAuth(ChangedUserAuth changedUserAuth);
    UserAuth toUserAuth(UserAuthDTO userAuthDTO);

    UserAuthDTO toDTOFromUserAuth(UserAuth userAuth);

    List<UserAuthDTO> toDTOsFromAuths(List<UserAuth> userAuths);
}
