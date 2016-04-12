package com.raphaelsolarski.issuetracker.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.raphaelsolarski.issuetracker.Application;
import com.raphaelsolarski.issuetracker.model.Issue;
import com.raphaelsolarski.issuetracker.repository.IssueRepository;
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

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class}, loader = AnnotationConfigWebContextLoader.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@WebAppConfiguration
public class IssueControllerIT {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        Issue issue = new Issue();
        issue.setTitle("Title1");
        issueRepository.save(issue);
    }

    @Test
    public void shouldInjectWebAppContext() throws Exception {
        assertNotNull(wac);
    }

    @Test
    public void getIssuesShouldReturnOkCode() throws Exception {
        mockMvc.perform(get("/issue").accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk());
    }

    @Test
    public void getIssueShouldReturnIssueFromDB() throws Exception {
        Issue issue = new Issue();
        issue.setTitle("Title");
        issue.setDescription("Description");
        Issue savedIssue = issueRepository.save(issue);
        Integer issueId = savedIssue.getId();

        mockMvc.perform(get("/issue/" + issueId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(issueId))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    public void getIssuesShouldReturnIssuesInJson() throws Exception {
        mockMvc.perform(get("/issue")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void addIssueShouldAddIssueToDB() throws Exception {
        Issue issueToAdd = new Issue();
        issueToAdd.setTitle("Title");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String issueJson = mapper.writeValueAsString(issueToAdd);

        String response = mockMvc.perform(post("/issue").contentType(MediaType.APPLICATION_JSON_UTF8).content(issueJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(response);
        Integer idFromResponse = JsonPath.parse(response).read("$.id");
        Assert.assertNotNull(idFromResponse);

        Issue issueFromDB = issueRepository.findOne(idFromResponse);
        Assert.assertNotNull(issueFromDB);
        Assert.assertEquals("Title", issueFromDB.getTitle());
    }

}