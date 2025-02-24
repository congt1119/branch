package com.kingcong.branch.exercise.controller;

import com.kingcong.branch.exercise.api.model.ApiUserData;
import com.kingcong.branch.exercise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{username}", produces = "application/json")
    public @ResponseBody ApiUserData getUserData(@PathVariable String username) throws ExecutionException {
        return userService.getUserData(username);
    }
}
