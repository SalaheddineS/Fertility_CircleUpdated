package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Repositories.UserRepository;
import com.POC.AuthSecurity.Security.JwtUtilities;
import com.POC.AuthSecurity.Services.Interfaces.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtilities jwtUtilities;
    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtilities jwtUtilities) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtilities = jwtUtilities;
    }

    @Override
    public String Authentication(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User Not Found"));
        return jwtUtilities.generateToken(user);
    }
}
