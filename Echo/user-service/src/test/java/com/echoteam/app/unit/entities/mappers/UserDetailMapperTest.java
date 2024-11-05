package com.echoteam.app.unit.entities.mappers;

import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.changedDTO.ChangedUserDetail;
import com.echoteam.app.entities.dto.createdDTO.CreatedDetail;
import com.echoteam.app.entities.dto.nativeDTO.UserDetailDTO;
import com.echoteam.app.entities.mappers.UserDetailMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailMapperTest {
    private final UserDetailMapper mapper = UserDetailMapper.INSTANCE;

    @Test
    void mapToDTO_fromCreatedDetailTest() {
        CreatedDetail createdDetail = CreatedDetail.getValidInstance();
        UserDetailDTO dto = mapper.toDTOFromCreatedDetail(createdDetail);

        assertThat(dto.getUserId()).isEqualTo(createdDetail.getUserId());
        assertThat(dto.getDateOfBirth()).isEqualTo(createdDetail.getDateOfBirth());
        assertThat(dto.getPhone()).isEqualTo(createdDetail.getPhone());

        assertThat(dto.getId()).isNull();
        assertThat(dto.getFirstname()).isNull();
        assertThat(dto.getLastname()).isNull();
        assertThat(dto.getPatronymic()).isNull();
        assertThat(dto.getSex()).isNull();
        assertThat(dto.getAbout()).isNull();
        assertThat(dto.getCreated()).isNull();
        assertThat(dto.getChanged()).isNull();
        assertThat(dto.getIsDeleted()).isNull();
    }

    @Test
    void mapToDTO_fromChangedDetailTest() {
        ChangedUserDetail changedDetail = ChangedUserDetail.getValidInstance();
        UserDetailDTO dto = mapper.toDTOFromChangedDetail(changedDetail);

        assertThat(dto.getId()).isEqualTo(changedDetail.getId());
        assertThat(dto.getFirstname()).isEqualTo(changedDetail.getFirstname());
        assertThat(dto.getLastname()).isEqualTo(changedDetail.getLastname());
        assertThat(dto.getPatronymic()).isEqualTo(changedDetail.getPatronymic());
        assertThat(dto.getSex()).isEqualTo(changedDetail.getSex());
        assertThat(dto.getDateOfBirth()).isEqualTo(changedDetail.getDateOfBirth());
        assertThat(dto.getPhone()).isEqualTo(changedDetail.getPhone());
        assertThat(dto.getAbout()).isEqualTo(changedDetail.getAbout());

        assertThat(dto.getCreated()).isNull();
        assertThat(dto.getChanged()).isNull();
        assertThat(dto.getIsDeleted()).isNull();
        assertThat(dto.getUserId()).isNull();
    }

    @Test
    void mapToUserDetail_fromDTOTest() {
        UserDetailDTO dto = UserDetailDTO.getValidInstance();
        UserDetail detail = mapper.toUserDetail(dto);

        assertThat(detail.getId()).isEqualTo(dto.getId());
        assertThat(detail.getFirstname()).isEqualTo(dto.getFirstname());
        assertThat(detail.getLastname()).isEqualTo(dto.getLastname());
        assertThat(detail.getPatronymic()).isEqualTo(dto.getPatronymic());
        assertThat(detail.getSex()).isEqualTo(dto.getSex());
        assertThat(detail.getDateOfBirth()).isEqualTo(dto.getDateOfBirth());
        assertThat(detail.getPhone()).isEqualTo(dto.getPhone());
        assertThat(detail.getAbout()).isEqualTo(dto.getAbout());
        assertThat(detail.getCreated()).isEqualTo(dto.getCreated());
        assertThat(detail.getChanged()).isEqualTo(dto.getChanged());
        assertThat(detail.getIsDeleted()).isEqualTo(dto.getIsDeleted());

        assertThat(detail.getUser()).isNull();
    }

    @Test
    void mapToDTO_fromUserDetailTest() {
        UserDetail userDetail = UserDetail.getValidInstance();
        UserDetailDTO dto = mapper.toDTOFromUserDetail(userDetail);

        assertThat(dto.getId()).isEqualTo(userDetail.getId());
        assertThat(dto.getUserId()).isEqualTo(userDetail.getUser().getId());
        assertThat(dto.getFirstname()).isEqualTo(userDetail.getFirstname());
        assertThat(dto.getLastname()).isEqualTo(userDetail.getLastname());
        assertThat(dto.getPatronymic()).isEqualTo(userDetail.getPatronymic());
        assertThat(dto.getSex()).isEqualTo(userDetail.getSex());
        assertThat(dto.getDateOfBirth()).isEqualTo(userDetail.getDateOfBirth());
        assertThat(dto.getPhone()).isEqualTo(userDetail.getPhone());
        assertThat(dto.getAbout()).isEqualTo(userDetail.getAbout());
        assertThat(dto.getCreated()).isEqualTo(userDetail.getCreated());
        assertThat(dto.getChanged()).isEqualTo(userDetail.getChanged());
        assertThat(dto.getIsDeleted()).isEqualTo(userDetail.getIsDeleted());
    }

}
