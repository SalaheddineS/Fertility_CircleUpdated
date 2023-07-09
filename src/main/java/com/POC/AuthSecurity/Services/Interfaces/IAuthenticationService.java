package com.POC.AuthSecurity.Services.Interfaces;

import com.POC.AuthSecurity.Entities.User;

public interface IAuthenticationService {

    String Authentication(String email, String password);
}
