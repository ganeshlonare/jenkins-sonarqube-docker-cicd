package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class userController {

    private final UserService userService;
    private final ModelMapper modelMapper;

//    public userController(UserService userService, ModelMapper modelMapper) {
//        this.userService = userService;
//        this.modelMapper = modelMapper;
//    }

    @PostMapping(path = "/user")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/user/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }

//    @GetMapping(path = "/search")
//    public ResponseEntity<String> searchQuery(@RequestParam("query") String query){
//        return new ResponseEntity<>("Search resuts for "+ query,HttpStatus.OK);
//    }

}