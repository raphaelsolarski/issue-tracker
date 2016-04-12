package com.raphaelsolarski.issuetracker.repository;

import com.raphaelsolarski.issuetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByLogin(String login);

}
