package com.echoteam.app.entities.mappers;

import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.changedDTO.ChangedUserDetail;
import com.echoteam.app.entities.dto.createdDTO.CreatedDetail;
import com.echoteam.app.entities.dto.nativeDTO.UserDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDetailMapper {
    UserDetailMapper INSTANCE = Mappers.getMapper(UserDetailMapper.class);

    UserDetailDTO toDTOFromCreatedDetail(CreatedDetail createdDetail);
    UserDetailDTO toDTOFromChangedDetail(ChangedUserDetail changedUserDetail);
    UserDetail toUserDetail(UserDetailDTO userDetailDTO);

    @Mapping(source = "user.id", target = "userId")
    UserDetailDTO toDTOFromUserDetail(UserDetail userDetail);

    List<UserDetailDTO> toDTOsFromUserDetails(List<UserDetail> userDetails);

}
