package com.POC.AuthSecurity.Services.Interfaces;

import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Repositories.UserRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    Optional<User> getUserByEmail(String username);
    String saveUser(User user);
    String deleteUser(String email);
    String updateUser(User user, String email);
    String assignRoleToUser(String email, String roleName);
    String removeRoleFromUser(String email, String roleName);
    String changeProgramToUser(String email, String ProgramName);
    String activateUser(String email);


}
