package com.semillero.ecosistema.controllers;

import com.semillero.ecosistema.dtos.UserDto;
import com.semillero.ecosistema.models.UserModel;
import com.semillero.ecosistema.request.UserRequest;
import com.semillero.ecosistema.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    final private UserService userService;
    public  UserController(UserService userService){this.userService = userService;}
    @GetMapping
    public List<UserDto> getAllUsers(){return this.userService.getAll();}

    @PostMapping
    public UserModel createUser(@Valid @RequestBody UserRequest userRequest){
        return this.userService.createUser(userRequest);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id){
        this.userService.deleteUser(id);
        return ResponseEntity
                .status(200)
                .build();
    }


}
