package com.echoteam.app.entities.dto.mappers;

import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.entityDTO.CreatedDetail;
import com.echoteam.app.entities.dto.entityDTO.UserDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDetailMapper {
    UserDetailMapper INSTANCE = Mappers.getMapper(UserDetailMapper.class);

    UserDetailDTO toDTOFromCreatedDetail(CreatedDetail createdDetail);
    UserDetail toUserDetail(UserDetailDTO userDetailDTO);

    UserDetailDTO toDTOFromUserDetail(UserDetail userDetail);
    CreatedDetail toCreatedDetail(UserDetailDTO userDetailDTO);

    List<UserDetailDTO> toDTOsFromCreatedDetails(List<CreatedDetail> createdDetails);
    List<UserDetail> toUserDetailsFromDTOs(List<UserDetailDTO> userDetailDTOs);

    List<UserDetailDTO> toDTOsFromUserDetails(List<UserDetail> userDetails);
    List<CreatedDetail> toCreatedDetailsFromDTOs(List<UserDetailDTO> userDetailDTOs);
}
