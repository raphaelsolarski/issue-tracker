package com.raphaelsolarski.issuetracker.controller;

import com.raphaelsolarski.issuetracker.model.User;
import com.raphaelsolarski.issuetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/{login}", method = RequestMethod.GET)
    User getUserByLogin(@PathVariable String login) {
        return userService.findByLogin(login);
    }
}
