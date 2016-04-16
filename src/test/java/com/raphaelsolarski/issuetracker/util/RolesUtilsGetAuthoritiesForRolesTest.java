package com.raphaelsolarski.issuetracker.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class RolesUtilsGetAuthoritiesForRolesTest {

    private RolesUtils sut = new RolesUtils();

    @Test
    public void shouldReturnAuthoritiesListWithOneElementForOneRoleString() throws Exception {
        String roles = "ROLE_USER";
        List<GrantedAuthority> result = sut.getAuthoritiesForRoles(roles);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(roles, result.get(0).getAuthority());
    }

    @Test
    public void shouldReturnAuthoritiesForTwoRoles() throws Exception {
        String roles = "ROLE_USER,ROLE_ADMIN";
        List<GrantedAuthority> result = sut.getAuthoritiesForRoles(roles);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("ROLE_USER", result.get(0).getAuthority());
        Assert.assertEquals("ROLE_ADMIN", result.get(1).getAuthority());
    }

    @Test
    public void shouldReturnEmptyListWhenRolesNull() throws Exception {
        List<GrantedAuthority> result = sut.getAuthoritiesForRoles(null);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }
}