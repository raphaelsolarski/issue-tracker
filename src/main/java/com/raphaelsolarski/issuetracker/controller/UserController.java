package com.raphaelsolarski.issuetracker.controller;

import com.raphaelsolarski.issuetracker.model.User;
import com.raphaelsolarski.issuetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/{login}", method = RequestMethod.GET)
    ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        User user = userService.findByLogin(login);
        if(user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }
}
