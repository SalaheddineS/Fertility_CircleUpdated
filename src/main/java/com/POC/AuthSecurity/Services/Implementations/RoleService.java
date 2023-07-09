package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.Entities.Role;
import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Repositories.RoleRepository;
import com.POC.AuthSecurity.Repositories.UserRepository;
import com.POC.AuthSecurity.Services.Interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public String saveRole(Role role) {
        if (roleRepository.findByName(role.getName()) == null) {
             roleRepository.save(role);
             return "Role saved successfully";
        }
        else throw new RuntimeException("Role already Exists");
    }

    @Override
    public String deleteRole(String name) {
        try {
            List<User> allUsers=userRepository.findAll();
            for (User user:allUsers) {
                for(Role role:user.getRoles()){
                    if(role.getName().equals(name)){
                        user.getRoles().remove(role);
                    }
                }
            }
            roleRepository.deleteByName(name);
            return "Role deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error whilst deleting Role");
        }
    }


}
