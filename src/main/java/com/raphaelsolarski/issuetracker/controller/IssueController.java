package com.raphaelsolarski.issuetracker.controller;

import com.raphaelsolarski.issuetracker.model.Issue;
import com.raphaelsolarski.issuetracker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Issue> getIssue(@PathVariable Integer id) {
        Issue issue = issueService.findById(id);
        if (issue != null) {
            return new ResponseEntity<Issue>(issue, HttpStatus.OK);
        }
        return new ResponseEntity<Issue>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    List<Issue> getIssues() {
        return issueService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    Issue addIssue(@RequestBody Issue issue) {
        return issueService.saveIssue(issue);
    }

}