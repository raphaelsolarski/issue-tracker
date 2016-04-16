package com.raphaelsolarski.issuetracker.service;

import com.raphaelsolarski.issuetracker.model.User;
import com.raphaelsolarski.issuetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User addUser(User user) {
        if (userRepository.findByLogin(user.getLogin()) == null) {
            return userRepository.save(user);
        } else {
            return null;
        }
    }

}
