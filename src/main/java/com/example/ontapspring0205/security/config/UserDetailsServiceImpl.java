package com.example.ontapspring0205.security.config;

import com.example.ontapspring0205.entity.login.UserEntity;
import com.example.ontapspring0205.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = iUserRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(userEntity);
    }
}
