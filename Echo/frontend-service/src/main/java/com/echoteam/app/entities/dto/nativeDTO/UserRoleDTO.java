package com.echoteam.app.entities.dto.nativeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleDTO {
    private Short id;
    private String name;

    public static List<UserRoleDTO> getValidInstanceList() {
        return List.of(
                new UserRoleDTO((short) 1,"USER"),
                new UserRoleDTO((short) 2,"ADMIN")
        );
    }

    public static UserRoleDTO getValidInstance() {
        return new UserRoleDTO((short) 1,"USER");
    }
}
