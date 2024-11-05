package com.echoteam.app.unit.entities.mappers;

import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.dto.changedDTO.ChangedUserAuth;
import com.echoteam.app.entities.dto.createdDTO.CreatedAuth;
import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import com.echoteam.app.entities.mappers.UserAuthMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAuthMapperTest {
    private final UserAuthMapper mapper = UserAuthMapper.INSTANCE;

    @Test
    void mapToDTO_fromCreatedAuthTest() {
        CreatedAuth createdAuth = CreatedAuth.getValidInstance();
        UserAuthDTO authDTO = mapper.toDTOFromCreatedAuth(createdAuth);

        assertThat(authDTO.getUserId()).isEqualTo(createdAuth.getUserId());
        assertThat(authDTO.getEmail()).isEqualTo(createdAuth.getEmail());
        assertThat(authDTO.getPassword()).isEqualTo(createdAuth.getPassword());

        assertThat(authDTO.getId()).isNull();
        assertThat(authDTO.getCreated()).isNull();
        assertThat(authDTO.getChanged()).isNull();
        assertThat(authDTO.getIsDeleted()).isNull();
    }

    @Test
    void mapToDTO_fromChangedAuthTest() {
        ChangedUserAuth changedAuth = ChangedUserAuth.getValidInstance();
        UserAuthDTO authDTO = mapper.toDTOFromChangedAuth(changedAuth);

        assertThat(authDTO.getId()).isEqualTo(changedAuth.getId());
        assertThat(authDTO.getEmail()).isEqualTo(changedAuth.getEmail());

        assertThat(authDTO.getUserId()).isNull();
        assertThat(authDTO.getPassword()).isNull();
        assertThat(authDTO.getCreated()).isNull();
        assertThat(authDTO.getChanged()).isNull();
        assertThat(authDTO.getIsDeleted()).isNull();
    }

    @Test
    void mapToUserAuth_fromDTO() {
        UserAuthDTO dto = UserAuthDTO.getValidInstance();
        UserAuth auth = mapper.toUserAuth(dto);

        assertThat(auth.getId()).isEqualTo(dto.getId());
        assertThat(auth.getEmail()).isEqualTo(dto.getEmail());
        assertThat(auth.getPassword()).isEqualTo(dto.getPassword());
        assertThat(auth.getCreated()).isEqualTo(dto.getCreated());
        assertThat(auth.getChanged()).isEqualTo(dto.getChanged());
        assertThat(auth.getIsDeleted()).isEqualTo(dto.getIsDeleted());

        assertThat(auth.getUser()).isNull();
    }

    @Test
    void mapToDTO_fromUserAuth() {
        UserAuth auth = UserAuth.getValidInstance();
        UserAuthDTO dto = mapper.toDTOFromUserAuth(auth);

        assertThat(dto.getId()).isEqualTo(auth.getId());
        assertThat(dto.getEmail()).isEqualTo(auth.getEmail());
        assertThat(dto.getPassword()).isEqualTo(auth.getPassword());
        assertThat(dto.getCreated()).isEqualTo(auth.getCreated());
        assertThat(dto.getChanged()).isEqualTo(auth.getChanged());
        assertThat(dto.getIsDeleted()).isEqualTo(auth.getIsDeleted());

        assertThat(dto.getUserId()).isNull();
    }

}
