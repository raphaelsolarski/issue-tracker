package com.raphaelsolarski.issuetracker.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RolesUtils {

    public List<GrantedAuthority> getAuthoritiesForRoles(String roles) {
        if(roles == null) {
            return new ArrayList<>();
        }
        String[] rolesArray = StringUtils.tokenizeToStringArray(roles, ",");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(String role : rolesArray) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return grantedAuthorities;
    }

}
