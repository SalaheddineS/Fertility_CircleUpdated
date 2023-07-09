package com.POC.AuthSecurity.Services.Interfaces;

import com.POC.AuthSecurity.Entities.Recipe;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRecipeService {


    //CREATE
    Recipe addRecipe(Recipe recipe);

    //READ
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(int id);
    Recipe getRecipeByName(String name);

    //UPDATE
    String updateRecipeById(int id, Recipe recipe);
    String updateRecipeByName(String name, Recipe recipe);

    //DELETE
    String deleteRecipeById(int id);
    String deleteRecipeByName(String name);
    String assignImageToRecipe(String recipe, MultipartFile file);
    String removeImageFromRecipe(String recipe);
    ResponseEntity<FileSystemResource> getImageRecipe(String recipe);
}