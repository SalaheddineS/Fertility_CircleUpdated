package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.Entities.Program;
import com.POC.AuthSecurity.Entities.Role;
import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Repositories.ProgramRepository;
import com.POC.AuthSecurity.Repositories.RoleRepository;
import com.POC.AuthSecurity.Repositories.UserRepository;
import com.POC.AuthSecurity.Security.JwtUtilities;
import com.POC.AuthSecurity.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtilities jwtUtilities;
    private final ProgramRepository programRepository;
    private final String PROFILE_PICTURE_PATH = "src/main/resources/ProfilePictures";

    BCryptPasswordEncoder bCryptPasswordEncoder (){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, JwtUtilities jwtUtilities, ProgramRepository programRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtilities = jwtUtilities;
        this.programRepository = programRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public String saveUser(User user) {
        try {
            user.setEmail(user.getEmail().toLowerCase());
            if (userRepository.findByEmail(user.getEmail()).isPresent()) { //we check if the email already exists
                throw new RuntimeException("Email already exists");
            }
            Role role = roleRepository.findByName("USER");  //we get the role from the database
            if (role == null) throw new RuntimeException("Role not found");

            if (user.getEmail() != null //we check if all fields are filled
                    && user.getPassword() != null) {

                String email = user.getEmail();
                String regex = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"; //email Regex

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (matcher.matches()) { // we verify the email regex
                    if (user.getRoles() != null) { //we check if the user has entered roles wich is not allowed
                        user.getRoles().clear(); //we clear the roles list
                    }
                    user.setProfilePicture(null);
                    user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
                    user.setActive(false);
                    user.getRoles().add(role); //we add the USER role to the user
                    userRepository.save(user);
                    return jwtUtilities.generateToken(user);
                } else throw new RuntimeException("Invalid email");

            } else throw new RuntimeException("Error whilst saving the user");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteUser(String email) {
        try {
            email = email.toLowerCase();
            if (userRepository.findByEmail(email).isEmpty()) throw new RuntimeException("User not found");
            userRepository.deleteByEmail(email);
            return "User deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateUser(User user, String email) {

        try {
            email = email.toLowerCase();
            /*AccessVerification.verifyAccess(email);*/
            User userInfo = userRepository.findByEmail(email).get();
            if (userInfo == null) throw new RuntimeException("User not found");
            if (user.getPassword() != null) {
                userInfo.setPassword(user.getPassword());
            }
            if (user.getAddress() != null) {
                userInfo.setAddress(user.getAddress());
            }
            if (user.getFullName() != null) {
                userInfo.setFullName(user.getFullName());
            }
            if (user.getNumber() != null) {
                String number = String.valueOf(user.getNumber());
                String regex = "^[0-9]{10}$"; // 10 digits
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(number);
                if (matcher.matches()) {
                    userInfo.setNumber(user.getNumber());
                } else throw new RuntimeException("Invalid number");
            }

            userInfo.setActive(userInfo.isActive());
            userRepository.save(userInfo);
            return "User updated successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String assignRoleToUser(String email, String roleName) {
        try {
            email = email.toLowerCase();
            Role currentRole = roleRepository.findByName(roleName);
            Optional<User> user = userRepository.findByEmail(email);
            if(user.get().getRoles().contains(currentRole)) throw new RuntimeException("User already has this role");
            user.get().getRoles().add(currentRole);
            return "Assigned Role " + roleName + " Successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error Assigning role to User");
        }
    }

    @Override
    public String removeRoleFromUser(String email, String roleName) {
        try {
            email = email.toLowerCase();
            Optional<User> user = userRepository.findByEmail(email);
            user.get().getRoles().removeIf(role -> role.getName().equals(roleName));
            return "Removed Role " + roleName + " Successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error removing role from User");
        }
    }

    @Override
    public String changeProgramToUser(String email, String ProgramName) {
        try {
            email = email.toLowerCase();
            Program program=programRepository.findProgramByname(ProgramName);
            if(program==null) throw new RuntimeException("Program not found");
            User user = userRepository.findByEmail(email).get();
            user.setProgram(program);
            return "Changed Program Successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error changing program to User");
        }
    }

    @Override
    public String activateUser(String email) {
        try {
            email = email.toLowerCase();
            User user = userRepository.findByEmail(email).get();
            user.setActive(true);
            return "User Activated Successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error Activating User");
        }
    }






}
