package com.branches.security.service;

import com.branches.security.model.UserDetailsImpl;
import com.branches.shared.dto.UserDto;
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
        UserDto foundUser = getUserByEmailService.execute(username);

        return new UserDetailsImpl(foundUser);
    }
}
