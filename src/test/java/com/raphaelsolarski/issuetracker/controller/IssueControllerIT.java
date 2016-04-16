package com.raphaelsolarski.issuetracker.controller;

import com.jayway.jsonpath.JsonPath;
import com.raphaelsolarski.issuetracker.Application;
import com.raphaelsolarski.issuetracker.JsonTestUtils;
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

    private static final String NON_EXISTENT_ISSUE_ID = "1234";
    private static final String ISSUE_TITLE = "Title";
    private static final String ISSUE_DESCRIPTION = "Description";
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        issueRepository.deleteAll();
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
        issue.setTitle(ISSUE_TITLE);
        issue.setDescription(ISSUE_DESCRIPTION);
        issue.setUserId(1);
        Issue savedIssue = issueRepository.save(issue);
        Integer issueId = savedIssue.getId();

        mockMvc.perform(get("/issue/" + issueId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(issueId))
                .andExpect(jsonPath("$.description").value(ISSUE_DESCRIPTION))
                .andExpect(jsonPath("$.title").value(ISSUE_TITLE));
    }

    @Test
    public void getIssuesShouldReturnIssuesInJson() throws Exception {
        mockMvc.perform(get("/issue")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void addIssueShouldAddIssueToDB() throws Exception {
        Issue issueToAdd = new Issue();
        issueToAdd.setTitle(ISSUE_TITLE);
        issueToAdd.setUserId(1);

        String issueJson = JsonTestUtils.getObjectAsJson(issueToAdd);

        String response = mockMvc.perform(post("/issue").contentType(MediaType.APPLICATION_JSON_UTF8).content(issueJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotNull(response);
        Integer idFromResponse = JsonPath.parse(response).read("$.id");
        Assert.assertNotNull(idFromResponse);

        Issue issueFromDB = issueRepository.findOne(idFromResponse);
        Assert.assertNotNull(issueFromDB);
        Assert.assertEquals(ISSUE_TITLE, issueFromDB.getTitle());
    }

    @Test
    public void requestForNonexistentIssueShouldReturn404() throws Exception {
        mockMvc.perform(get("/issue/" + NON_EXISTENT_ISSUE_ID).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isNotFound());
    }
}