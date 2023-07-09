package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.Entities.Recipe;
import com.POC.AuthSecurity.Services.Implementations.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("recipe")
public class RecipeController {
    
    RecipeService recipeService;
    //CREATE
    @PostMapping("addRecipe")
    public Recipe addRecipe(@RequestBody Recipe Recipe) {
        return recipeService.addRecipe(Recipe);
    }

    //READ
    @GetMapping("getAllRecipes")
    public List<Recipe> getAllRecipe() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("getRecipeById/{id}")
    public Recipe getRecipeById(@PathVariable int id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("getRecipeByName/{name}")
    public Recipe getRecipeByName(@PathVariable String name) {
        return recipeService.getRecipeByName(name);
    }

    //UPDATE
    @PatchMapping("updateRecipeByName/{name}")
    public String updateRecipeByName(@PathVariable String name, @RequestBody Recipe newRecipe) {
        return recipeService.updateRecipeByName(name, newRecipe);
    }

    @PatchMapping("updateRecipeById/{id}")
    public String updateRecipeById(@PathVariable int id, @RequestBody Recipe newRecipe) {
        return recipeService.updateRecipeById(id, newRecipe);
    }
    //DELETE
    @DeleteMapping("deleteRecipeById/{id}")
    public String deleteRecipeById(@PathVariable int id) {
        return recipeService.deleteRecipeById(id);
    }

    @DeleteMapping("deleteRecipeByName/{name}")
    public String deleteRecipeByName(@PathVariable String name) {
        return recipeService.deleteRecipeByName(name);
    }

    @PostMapping("addRecipePicture/{recipeName}")
    public String addRecipePicture(@PathVariable String recipeName, @RequestParam("recipePicture") MultipartFile recipePicture) {
        return recipeService.addRecipePicture(recipeName, recipePicture);
    }
    @GetMapping("getRecipePicture/{recipeName}")
    public ResponseEntity<FileSystemResource> getRecipePicture(@PathVariable String recipeName) {
        return recipeService.getRecipePicture(recipeName);
    }
    @PatchMapping("changeRecipePictureName/{recipeName}/{newRecipeName}")
    public String changeRecipePictureName(@PathVariable String recipeName, @PathVariable String newRecipeName) {
        return recipeService.changeRecipePictureName(recipeName, newRecipeName);
    }
}
