package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Services.Implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("User")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/getByEmail")
    public User getUserByEmail(@RequestBody String email) {
        return userService.getUserByEmail(email).get();
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestBody User user) {
        return userService.deleteUser(user.getEmail());
    }

    @PutMapping("/update/{email}")
    public String updateUser(@RequestBody User user, @PathVariable String email) {
        return userService.updateUser(user, email);
    }

    @PutMapping("/assignRoleToUser")
    public String assignRoleToUser(@RequestBody Map<String,String> body) {
        return userService.assignRoleToUser(body.get("email"), body.get("name"));
    }

    @PutMapping("/removeRoleFromUser")
    public String removeRoleFromUser(@RequestBody Map<String,String> body) {
        return userService.removeRoleFromUser(body.get("email"), body.get("name"));
    }

    @PutMapping("/changeProgramToUser")
    public String changeProgramToUser(@RequestBody Map<String,String> body) {
        return userService.changeProgramToUser(body.get("email"), body.get("programName"));
    }

    @PutMapping("/activateUser")
    public String activateUser(@RequestBody String email) {
        return userService.activateUser(email);
    }

    @PutMapping("/addProfilePicture/{email}")
    public String addProfilePicture(@PathVariable String email,@RequestParam("profilePicture") MultipartFile profilePicture) {
        return userService.addProfilePicture(email, profilePicture);
    }

    @GetMapping("/getProfilePicture/{email}")
    public ResponseEntity<FileSystemResource> getProfilePicture(@PathVariable String email) {
        return userService.getProfilePicture(email);
    }
}
