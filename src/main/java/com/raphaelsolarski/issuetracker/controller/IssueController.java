package com.raphaelsolarski.issuetracker.controller;

import com.raphaelsolarski.issuetracker.model.Issue;
import com.raphaelsolarski.issuetracker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    Issue getIssue(@PathVariable Integer id) {
        return issueService.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    List<Issue> getIssues() {
        return null;
    }

}