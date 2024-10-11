package com.echoteam.app.entities.dto.mappers;

import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.entityDTO.changed.ChangedUserDetail;
import com.echoteam.app.entities.dto.entityDTO.created.CreatedDetail;
import com.echoteam.app.entities.dto.entityDTO.UserDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDetailMapper {
    UserDetailMapper INSTANCE = Mappers.getMapper(UserDetailMapper.class);

    UserDetailDTO toDTOFromCreatedDetail(CreatedDetail createdDetail);
    UserDetailDTO toDTOFromChangedDetail(ChangedUserDetail changedUserDetail);
    UserDetail toUserDetail(UserDetailDTO userDetailDTO);

    UserDetailDTO toDTOFromUserDetail(UserDetail userDetail);

    List<UserDetailDTO> toDTOsFromUserDetails(List<UserDetail> userDetails);

}
