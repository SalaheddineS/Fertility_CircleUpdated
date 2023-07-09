package com.POC.AuthSecurity.Repositories;

import com.POC.AuthSecurity.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String roleName);
    void deleteByName(String name);
}
