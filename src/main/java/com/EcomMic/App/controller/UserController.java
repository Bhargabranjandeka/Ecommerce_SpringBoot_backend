package com.EcomMic.App.controller;

import com.EcomMic.App.dto.UserRequest;
import com.EcomMic.App.dto.UserResponse;
import com.EcomMic.App.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return userService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.save(userRequest);
        return ResponseEntity.ok().body("User Created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
        boolean updateUser =userService.updateUser(id,userRequest);

        if(updateUser){
            return ResponseEntity.ok("User Updated Successfully");
        }

        return ResponseEntity.notFound().build();
    }
}
