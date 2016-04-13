package com.raphaelsolarski.issuetracker.controller;

import com.jayway.jsonpath.JsonPath;
import com.raphaelsolarski.issuetracker.Application;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class}, loader = AnnotationConfigWebContextLoader.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@WebAppConfiguration
public class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getUserByLoginShouldReturnJsonWithUser() throws Exception {
        final String LOGIN = "user_login";
        User user = new User();
        user.setLogin(LOGIN);
        userRepository.save(user);

        String responseContent = mockMvc.perform(get("/user/" + LOGIN).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String loginFromResponse = JsonPath.parse(responseContent).read("$.login");
        Assert.assertEquals(LOGIN, loginFromResponse);
    }

    @Test
    public void requestForNonexistentUserShouldReturn404() throws Exception {
        mockMvc.perform(get("/user/1234").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

}