package com.woozuda.backend.account.service;

import com.woozuda.backend.account.dto.CustomUserDetails;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByUsername(username);

        if(userData != null){
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
