package com.branches.auth.service;

import com.branches.auth.model.UserDetailsImpl;
import com.branches.user.domain.UserEntity;
import com.branches.user.service.GetUserByEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final GetUserByEmailService getUserByEmailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity foundUser = getUserByEmailService.execute(username);

        return new UserDetailsImpl(foundUser);
    }
}
