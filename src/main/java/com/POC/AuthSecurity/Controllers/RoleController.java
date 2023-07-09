package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.Entities.Role;
import com.POC.AuthSecurity.Services.Implementations.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("Role")
public class RoleController {
    private final RoleService roleService;
    @GetMapping("getAll")
    public List<Role> getAllRoles() {
        return roleService.getRoles();
    }

    @GetMapping("getByName")
    public Role getRoleByName(@RequestBody Role name) {
        return roleService.getRoleByName(name.getName());
    }

    @PostMapping("save")
    public String saveRole(@RequestBody  Role role) {
        return roleService.saveRole(role);
    }

    @DeleteMapping("delete") //Dangereux , ne pas utiliser qu'en cas exteremes
    public String deleteRole(@RequestBody Role role) {
        return roleService.deleteRole(role.getName());
    }
}
