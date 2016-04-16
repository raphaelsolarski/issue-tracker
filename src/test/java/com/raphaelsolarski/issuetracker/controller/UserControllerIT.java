package com.raphaelsolarski.issuetracker.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.raphaelsolarski.issuetracker.Application;
import com.raphaelsolarski.issuetracker.JsonTestUtils;
import com.raphaelsolarski.issuetracker.model.User;
import com.raphaelsolarski.issuetracker.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.raphaelsolarski.issuetracker.JsonTestUtils.getObjectAsJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class}, loader = AnnotationConfigWebContextLoader.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@WebAppConfiguration
public class UserControllerIT {

    private static final String USER_LOGIN = "user_login";
    private static final String NON_EXISTENT_USER_ID = "1234";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        userRepository.deleteAll();
    }

    @Test
    public void getUserByLoginShouldReturnJsonWithUser() throws Exception {
        final String LOGIN = USER_LOGIN;
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword("");
        userRepository.save(user);

        String responseContent = mockMvc.perform(get("/user/" + LOGIN).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String loginFromResponse = JsonPath.parse(responseContent).read("$.login");
        Assert.assertEquals(LOGIN, loginFromResponse);
    }

    @Test
    public void requestForNonexistentUserShouldReturn404() throws Exception {
        mockMvc.perform(get("/user/"+ NON_EXISTENT_USER_ID).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addUserShouldAddUserToDB() throws Exception {
        User userToAdd = new User();
        userToAdd.setLogin(USER_LOGIN);
        userToAdd.setPassword("");
        String userJson = getObjectAsJson(userToAdd);

        String response = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(response);

        Integer idFromResponse = JsonPath.parse(response).read("$.id");
        Assert.assertNotNull(idFromResponse);

        User userFromDB = userRepository.findOne(idFromResponse);
        Assert.assertNotNull(userFromDB);
        Assert.assertEquals(USER_LOGIN, userFromDB.getLogin());
    }

    @Test
    public void tryToAddUserWithTheSameLoginShouldReturn409() throws Exception {
        User userToAdd = new User();
        userToAdd.setLogin(USER_LOGIN);
        userToAdd.setPassword("");
        userRepository.save(userToAdd);

        String userJson = getObjectAsJson(userToAdd);

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteUserShouldDeleteUserFromDB() throws Exception {
        User userToAdd = new User();
        userToAdd.setLogin(USER_LOGIN);
        userToAdd.setPassword("");
        Integer id = userRepository.save(userToAdd).getId();

        mockMvc.perform(delete("/user/login/" + USER_LOGIN))
                .andExpect(status().isOk());

        Assert.assertFalse(userRepository.exists(id));
    }
}