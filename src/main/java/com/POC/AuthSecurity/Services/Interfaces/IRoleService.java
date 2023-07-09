package com.POC.AuthSecurity.Services.Interfaces;

import com.POC.AuthSecurity.Entities.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles();
    Role getRoleByName(String name);
    String saveRole(Role role);
    String deleteRole(String email);
}