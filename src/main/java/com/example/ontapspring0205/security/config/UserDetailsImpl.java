package com.example.ontapspring0205.security.config;

import com.example.ontapspring0205.entity.login.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private UserEntity userEntity;

    public UserDetailsImpl(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<RoleEntity> roles = userEntity.getRoleEntityList();
//
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        for (RoleEntity role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getRole()));
//        }
//        return authorities;

        return Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
