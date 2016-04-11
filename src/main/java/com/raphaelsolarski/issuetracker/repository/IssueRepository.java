package com.raphaelsolarski.issuetracker.repository;

import com.raphaelsolarski.issuetracker.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Integer>{
}
