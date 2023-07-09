package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.Services.Implementations.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("addProfilePicture/{email}")
    public String addProfilePicture(@PathVariable String email, @RequestParam MultipartFile profilePicture){
        return imageService.addProfilePicture(email, profilePicture);
    }
    @GetMapping("getProfilePicture/{email}")
    public ResponseEntity<FileSystemResource> getProfilePicture(@PathVariable String email){
        return imageService.getProfilePicture(email);
    }
    @PostMapping("addRecipePicture/{recipeName}")
    public String addRecipePicture(@PathVariable String recipeName,@RequestParam MultipartFile recipePicture){
        return imageService.addRecipePicture(recipeName,recipePicture);
    }
    @GetMapping("getRecipePicture/{recipeName}")
    public ResponseEntity<FileSystemResource> getRecipePicture(@PathVariable String recipeName){
        return imageService.getRecipePicture(recipeName);
    }
    @DeleteMapping("removeProfilePicture/{email}")
    public String removeProfilePicture(@PathVariable String email){
        return imageService.removeProfilePicture(email);
    }
    @DeleteMapping("removeRecipePicture/{recipeName}")
    public String removeRecipePicture(@PathVariable String recipeName){
        return imageService.removeRecipePicture(recipeName);
    }

    @PatchMapping("updateRecipePictureName/{oldName}/{newName}")
    public String updateRecipePictureName(@PathVariable String oldName, @PathVariable String newName){
        return imageService.updateRecipePictureName(oldName, newName);
    }

}
