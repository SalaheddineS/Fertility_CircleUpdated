package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Services.Implementations.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("login")
    public String login(@RequestBody User user){
        return authenticationService.Authentication(user.getEmail(),user.getPassword());
    }
}
