package com.echoteam.app.repository;

import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private static String userServiceBasicUrl;
    private static String userServiceUserUrl;
    private final RestTemplate template;

    @Value("${service.user-service.url.basic}")
    void setBasicUrl(String basicUrl) {
        UserRepositoryImpl.userServiceBasicUrl = basicUrl;

    }

    @Value("${service.user-service.url.user}")
    void setUserUrl(String userUrl) {
        UserRepositoryImpl.userServiceUserUrl = userUrl;
    }

    @Override
    public UserDTO getUserByNickname(String nickname) {
        ResponseEntity<UserDTO> response = template.getForEntity(
                userServiceBasicUrl + userServiceUserUrl + "/userByNickname?nickname=%s".formatted(nickname),
                UserDTO.class
        );

        return response.getBody();
    }

}
