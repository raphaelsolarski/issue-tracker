package com.raphaelsolarski.issuetracker.service;

import com.google.common.collect.Lists;
import com.raphaelsolarski.issuetracker.model.User;
import com.raphaelsolarski.issuetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if(user != null){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_BASIC");
            return new org.springframework.security.core.userdetails.User(login, "user", Lists.newArrayList(grantedAuthority));
        } else {
            throw new UsernameNotFoundException("Username " + login + " not found");
        }
    }

}
