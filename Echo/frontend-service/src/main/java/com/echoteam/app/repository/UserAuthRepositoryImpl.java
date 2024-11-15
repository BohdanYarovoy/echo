package com.echoteam.app.repository;

import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {
    private static String userServiceBasicUrl;
    private static String userServiceAuthUrl;
    private final RestTemplate template;

    @Value("${service.user-service.url.basic}")
    void setBasicUrl(String basicUrl) {
        UserAuthRepositoryImpl.userServiceBasicUrl = basicUrl;
    }

    @Value("${service.user-service.url.auth}")
    void setAuthUrl(String authUrl) {
        UserAuthRepositoryImpl.userServiceAuthUrl = authUrl;
    }

    @Override
    public UserAuthDTO getAuthById(Long id) {
        ResponseEntity<UserAuthDTO> response = template.getForEntity(
                userServiceBasicUrl + userServiceAuthUrl + "/%s".formatted(id),
                UserAuthDTO.class
        );

        return response.getBody();
    }

}
