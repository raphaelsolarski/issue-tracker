package com.raphaelsolarski.issuetracker.service;

import com.raphaelsolarski.issuetracker.model.User;
import com.raphaelsolarski.issuetracker.repository.UserRepository;
import com.raphaelsolarski.issuetracker.util.RolesUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class UserSecurityServiceTest {

    @Mock
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private RolesUtils rolesUtils = new RolesUtils();

    @InjectMocks
    private UserSecurityService sut = new UserSecurityService(rolesUtils);

    @Test
    public void shouldReturnUserWhenLoginFigureInDB() throws Exception {
        User userFromDB = new User();
        userFromDB.setLogin("user_login");
        userFromDB.setId(1);
        userFromDB.setRoles(null);
        userFromDB.setPassword("");
        Mockito.when(userRepository.findByLogin("user_login")).thenReturn(userFromDB);

        UserDetails result = sut.loadUserByUsername("user_login");

        Assert.assertNotNull(result);
        Assert.assertEquals("user_login", result.getUsername());
        Assert.assertEquals(0, result.getAuthorities().size());
    }

    @Test
    public void shouldReturnAdditionalAuthoritiesWhenThereAreInDB() throws Exception {
        User userFromDB = new User();
        userFromDB.setLogin("user_login");
        userFromDB.setId(1);
        userFromDB.setRoles("ROLE_ADMIN,ROLE_BASIC");
        userFromDB.setPassword("");
        Mockito.when(userRepository.findByLogin("user_login")).thenReturn(userFromDB);

        UserDetails result = sut.loadUserByUsername("user_login");

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.getAuthorities().size());
    }

    @Test
    public void shouldReturnUserWithPasswordFromDB() throws Exception {
        User userFromDB = new User();
        userFromDB.setLogin("user_login");
        userFromDB.setPassword("password");
        Mockito.when(userRepository.findByLogin("user_login")).thenReturn(userFromDB);

        UserDetails result = sut.loadUserByUsername("user_login");

        Assert.assertNotNull(result);
        Assert.assertEquals("password", result.getPassword());
    }
}