package com.spring.user.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.user.management.dao.UserDAOImpl;
import com.spring.user.management.dto.UserResponseDto;
import com.spring.user.management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserDAOImpl userDAO;

    @GetMapping("/all")
    @ResponseBody
    public List<UserResponseDto> getAllUsers(){

        return userDAO.getAll();
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int userId){
        UserResponseDto userResponseDto = userDAO.getOne(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<UserResponseDto> addUser(@RequestBody User user){
        UserResponseDto objUserResponseDto = userDAO.addUser(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(objUserResponseDto);
    }

    @PutMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<UserResponseDto> modifyUser(@PathVariable int userId , @RequestBody User user) throws JsonProcessingException {
        UserResponseDto userResponseDto = userDAO.modifyUser(user , userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable int userId){

        UserResponseDto userResponseDto = userDAO.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
}
