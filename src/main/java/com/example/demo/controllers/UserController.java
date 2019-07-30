package com.example.demo.controllers;

import com.example.demo.DemoApplication;
import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity listAllUser() {
        LOG.info("Method listAllUser start...");
        List<User> users = (List<User>) userService.findAllUser();
        if (users.isEmpty()) {
            LOG.info("users.isEmpty()");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        LOG.info("Method getUser start...");
        if (userService.findById(id) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        LOG.info("Method getUser start...");
        if (userService.isUserExist(user)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public @ResponseBody ResponseEntity<?> updateUser(@PathVariable Long id,
                                                      @RequestBody User user) {
        LOG.info("Method updateUser start...");
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setAge(user.getAge());
        currentUser.setAboutMyself(user.getAboutMyself());

        userService.saveUser(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public @ResponseBody ResponseEntity<?> deleteUser(@PathVariable Long id) {
        LOG.info("Method deleteUser start...");
        if (userService.findById(id) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
