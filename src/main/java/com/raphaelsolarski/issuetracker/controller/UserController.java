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
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<User> addUser(@RequestBody User user) {
        User userFromDB = userService.addUser(user);
        if(userFromDB != null) {
            return new ResponseEntity<>(userFromDB, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}