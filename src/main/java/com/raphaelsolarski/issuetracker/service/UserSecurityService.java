package com.raphaelsolarski.issuetracker.service;

import com.google.common.collect.Lists;
import com.raphaelsolarski.issuetracker.model.User;
import com.raphaelsolarski.issuetracker.repository.UserRepository;
import com.raphaelsolarski.issuetracker.util.RolesUtils;
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

    @Autowired
    private RolesUtils rolesUtils;

    public UserSecurityService() {
    }

    public UserSecurityService(RolesUtils rolesUtils) {
        this.rolesUtils = rolesUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(login, user.getPassword(), rolesUtils.getAuthoritiesForRoles(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("Username " + login + " not found");
        }
    }

}
