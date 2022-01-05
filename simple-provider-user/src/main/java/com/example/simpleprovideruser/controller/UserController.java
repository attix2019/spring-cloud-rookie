package com.example.simpleprovideruser.controller;

import com.example.simpleprovideruser.dao.UserRepository;
import com.example.simpleprovideruser.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value="/{id}")
    public User findById(@PathVariable Long id){
        return userRepository.findById(id).get();
    }
}
