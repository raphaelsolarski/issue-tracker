package com.raphaelsolarski.issuetracker.service;

import com.raphaelsolarski.issuetracker.model.Issue;
import com.raphaelsolarski.issuetracker.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public Issue findById(Integer id) {
        return issueRepository.findOne(id);
    }

    public List<Issue> findAll() {
        return issueRepository.findAll();
    }
}
