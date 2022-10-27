package com.myproject.expensetrackerapi.controller;

import com.myproject.expensetrackerapi.entity.User;
import com.myproject.expensetrackerapi.model.UserModel;
import com.myproject.expensetrackerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

   /* @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }
*/
    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return new ResponseEntity<>(userService.read(id),HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id) {
        User mUser =userService.update(user,id);
        return new ResponseEntity<>(mUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> readUser() {
        return new ResponseEntity<>(userService.readUser(),HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel user) {
        User mUser =userService.updateUser(user);
        return new ResponseEntity<>(mUser, HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }









}
