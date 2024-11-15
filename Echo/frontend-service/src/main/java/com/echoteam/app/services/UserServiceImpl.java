package com.echoteam.app.services;

import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import com.echoteam.app.repository.UserAuthRepository;
import com.echoteam.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userByNickname = userRepository.getUserByNickname(username);
        UserAuthDTO authById = authRepository.getAuthById(userByNickname.getUserAuth().getId());

        Set<GrantedAuthority> roles = userByNickname.getRoles().stream()
                .map(e -> "ROLE_" + e.getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return User.builder()
                .username(userByNickname.getNickname())
                .password(authById.getPassword())
                .authorities(roles)
                .build();
    }

}
