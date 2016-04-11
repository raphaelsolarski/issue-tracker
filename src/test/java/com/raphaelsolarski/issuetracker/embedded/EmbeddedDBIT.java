package com.raphaelsolarski.issuetracker.embedded;

import com.raphaelsolarski.issuetracker.Application;
import com.raphaelsolarski.issuetracker.model.Issue;
import com.raphaelsolarski.issuetracker.repository.IssueRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class}, loader = AnnotationConfigWebContextLoader.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
@WebAppConfiguration
public class EmbeddedDBIT {

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void testEmbeddedDB() throws Exception {
        Issue issue = new Issue();
        issue.setDescription("test");
        issue.setTitle("test");
        Integer id = issueRepository.save(issue).getId();
        Issue actual = issueRepository.findOne(id);
        Assert.assertNotNull(actual);
    }

}
