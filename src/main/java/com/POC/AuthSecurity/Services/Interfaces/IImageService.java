package com.POC.AuthSecurity.Services.Interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    String addProfilePicture(String email, MultipartFile profilePicture);
    ResponseEntity<FileSystemResource> getProfilePicture(String email);
    String addRecipePicture(String recipeName, MultipartFile recipePicture);
    ResponseEntity<FileSystemResource> getRecipePicture(String recipeName);
    String removeProfilePicture(String email);
    String removeRecipePicture(String recipeName);
}
