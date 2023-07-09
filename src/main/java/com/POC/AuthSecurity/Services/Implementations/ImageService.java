package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.Entities.Image;
import com.POC.AuthSecurity.Entities.Recipe;
import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Repositories.ImageRepository;
import com.POC.AuthSecurity.Repositories.RecipeRepository;
import com.POC.AuthSecurity.Repositories.UserRepository;
import com.POC.AuthSecurity.Services.Interfaces.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
@Transactional
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public String addProfilePicture(String email, MultipartFile profilePicture) {
        if (profilePicture.isEmpty()) throw new RuntimeException("Profile picture is empty");
        if (userRepository.findByEmail(email).isEmpty()) throw new RuntimeException("User not found");
        String extension = profilePicture.getOriginalFilename().substring(profilePicture.getOriginalFilename().lastIndexOf(".") + 1);
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path savingPath = Paths.get(currentPath.toString(), "src", "main", "resources", "assets", "ProfilePictures", email + "." + extension);

        try {
            // Read the uploaded image into a BufferedImage
            BufferedImage originalImage = ImageIO.read(profilePicture.getInputStream());
            // Resize the image to 400x400
            BufferedImage resizedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, 400, 400, null);
            g.dispose();

            // Save the resized image to the specified path using transferTo()
            File resizedFile = savingPath.toFile();
            ImageIO.write(resizedImage, extension, resizedFile);
            //Verify if image for user exists , if its empty we create a new one , if not we just override the image by ignoring this
            if(imageRepository.findByName(email).isEmpty())
            {
            Image image = Image.builder()
                    .name(email)
                    .type(extension)
                    .build();
            imageRepository.save(image);
            User user = userRepository.findByEmail(email).get();
            user.setProfilePicture(image);
            userRepository.save(user);
            }
            return "Profile picture added successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while adding profile picture, here are the details: " + e);
        }
    }

    @Override
    public ResponseEntity<FileSystemResource> getProfilePicture(String email) {
        if (userRepository.findByEmail(email).isEmpty()) throw new RuntimeException("User not found");
        try
        {
            Image image = imageRepository.findByName(email).orElseThrow(() -> new RuntimeException("Profile picture not found"));
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/assets/ProfilePictures/" + image.getName() + "." + image.getType());
            if (!file.exists()) throw new RuntimeException("Error whilst getting the profile picture");
            FileSystemResource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            if(image.getType().equals("png"))
            {
                headers.setContentType(MediaType.IMAGE_PNG);
            }
            if(image.getType().equals("jpg") || image.getType().equals("jpeg") )
            {
                headers.setContentType(MediaType.IMAGE_JPEG);
            }
            if(image.getType().equals("gif"))
            {
                headers.setContentType(MediaType.IMAGE_GIF);
            }
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            throw new RuntimeException("Error while getting profile picture, here are the details : " + e);
        }
    }

    @Override
    public String addRecipePicture(String recipeName, MultipartFile RecipePicture) {
        if (RecipePicture.isEmpty()) throw new RuntimeException("Recipe picture is empty");
        if (recipeRepository.findRecipeByName(recipeName).isEmpty()) throw new RuntimeException("Recipe not found");
        String extension = RecipePicture.getOriginalFilename().substring(RecipePicture.getOriginalFilename().lastIndexOf(".") + 1);

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path savingPath = Paths.get(currentPath.toString(), "src", "main", "resources", "assets", "RecipePictures", recipeName + "." + extension);

        try {
            // Read the uploaded image into a BufferedImage
            BufferedImage originalImage = ImageIO.read(RecipePicture.getInputStream());
            // Resize the image to 400x400
            BufferedImage resizedImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();

            // Set the rendering hint to improve the interpolation quality
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            g.drawImage(originalImage, 0, 0, 800, 800, null);
            g.dispose();

            // Save the resized image to the specified path using transferTo()
            File resizedFile = savingPath.toFile();
            ImageIO.write(resizedImage, extension, resizedFile);
            if(imageRepository.findByName(recipeName).isEmpty())
            {
                Image image = Image.builder()
                        .name(recipeName)
                        .type(extension)
                        .build();
                imageRepository.save(image);
                Recipe recipe = recipeRepository.findRecipeByName(recipeName).get();
                recipe.setRecipePicture(image);
                recipeRepository.save(recipe);
            }
            return "Recipe picture added successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error while adding Recipe picture, here are the details: " + e);
        }
    }

    @Override
    public ResponseEntity<FileSystemResource> getRecipePicture(String recipeName) {
        try
        {
            if (recipeRepository.findRecipeByName(recipeName).isEmpty()) throw new RuntimeException("Recipe not found");
            Image image = imageRepository.findByName(recipeName).orElseThrow(() -> new RuntimeException("Recipe picture not found"));
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/assets/RecipePictures/" + image.getName() + "." + image.getType());
            if (!file.exists()) throw new RuntimeException("Error whilst getting the Recipe picture");
            FileSystemResource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            if(image.getType().equals("png"))
            {
                headers.setContentType(MediaType.IMAGE_PNG);
            }
            if(image.getType().equals("jpg") || image.getType().equals("jpeg") )
            {
                headers.setContentType(MediaType.IMAGE_JPEG);
            }
            if(image.getType().equals("gif"))
            {
                headers.setContentType(MediaType.IMAGE_GIF);
            }
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            throw new RuntimeException("Error while getting recipe picture, here are the details : " + e);
        }
}

    @Override
    public String removeProfilePicture(String email) {
        Image image = imageRepository.findByName(email).orElseThrow(() -> new RuntimeException("Profile picture not found"));
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/assets/ProfilePictures/" + image.getName() + "." + image.getType());
        if (!file.exists()) throw new RuntimeException("Error whilst getting the profile picture");
        file.delete();
        imageRepository.delete(image);
        User user = userRepository.findByEmail(email).get();
        user.setProfilePicture(null);
        userRepository.save(user);
        return "Profile picture deleted successfully";
    }

    @Override
    public String removeRecipePicture(String recipeName) {
        Image image = imageRepository.findByName(recipeName).orElseThrow(() -> new RuntimeException("Recipe picture not found"));
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/assets/RecipePictures/" + image.getName() + "." + image.getType());
        if (!file.exists()) throw new RuntimeException("Error whilst getting the Recipe picture");
        file.delete();
        imageRepository.delete(image);
        Recipe recipe = recipeRepository.findRecipeByName(recipeName).get();
        recipe.setRecipePicture(null);
        recipeRepository.save(recipe);
        return "Recipe picture deleted successfully";
    }

    @Override
    public String updateRecipePictureName(String oldName, String newName) {
        if (recipeRepository.findRecipeByName(oldName).isEmpty()) throw new RuntimeException("Recipe not found");
        Image image = imageRepository.findByName(oldName).get();
        if (image.getName().equals(newName)) throw new RuntimeException("New name is the same as the old one");
        if (imageRepository.findByName(newName).isPresent()) throw new RuntimeException("New name already exists");
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/assets/RecipePictures/" + image.getName() + "." + image.getType());
        if (!file.exists()) throw new RuntimeException("Error whilst getting the Recipe picture");
        file.renameTo(new File(System.getProperty("user.dir") + "/src/main/resources/assets/RecipePictures/" + newName + "." + image.getType()));
        image.setName(newName);
        imageRepository.save(image);
        return "Recipe picture name updated successfully";
    }
}
