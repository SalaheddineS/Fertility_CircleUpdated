package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.Entities.Recipe;
import com.POC.AuthSecurity.Repositories.RecipeRepository;
import com.POC.AuthSecurity.Services.Interfaces.IRecipeService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RecipeService implements IRecipeService {
    RecipeRepository recipeRepository;
    //CREATE
    @Override
    public Recipe addRecipe(Recipe recipe) {
        try
        {
            if (recipeRepository.findRecipeByName(recipe.getName()) != null) throw new RuntimeException("Recipe already exists");
            return recipeRepository.save(recipe);
        }
        catch (Exception e) {
            throw new RuntimeException("Error while adding recipe, here are the details : " + e);
        }
    }
    //READ
    @Override
    public List<Recipe> getAllRecipes() {
        try
        {
            return recipeRepository.findAll();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while getting all recipes, here are the details : " + e);
        }
    }
    @Override
    public Recipe getRecipeById(int id) {
        try
        {
            return recipeRepository.findById(id).get();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while getting recipe by id, here are the details : " + e);
        }
    }

    @Override
    public Recipe getRecipeByName(String name) {
        try
        {
            return recipeRepository.findRecipeByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Recipe not found");
        }
    }

    //UPDATE
    @Override
    public String updateRecipeById(int id, Recipe newRecipe) {
        try {
            Recipe recipe = getRecipeById(id);
            if (newRecipe.getName() != null)
            {
                changeRecipePictureName(recipe.getName(), newRecipe.getName());
                recipe.setName(newRecipe.getName());
            }
            if (newRecipe.getIngredients() != null)
            {
                recipe.setIngredients(newRecipe.getIngredients());
            }
            if(newRecipe.getInstructions() != null)
            {
                recipe.setInstructions(newRecipe.getInstructions());
            }
            recipeRepository.save(recipe);
            return "Recipe successfully updated";
        } catch (Exception e) {
            throw new RuntimeException("Recipe not found");
        }
    }

    @Override
    public String updateRecipeByName(String name, Recipe newRecipe) {
        try
        {
        Recipe recipe = getRecipeByName(name);
            if (newRecipe.getIngredients() != null)
            {
                recipe.setIngredients(newRecipe.getIngredients());
            }
            if(newRecipe.getInstructions() != null)
            {
                recipe.setInstructions(newRecipe.getInstructions());
            }

        recipeRepository.save(recipe);
        return "Recipe successfully updated";
        }
        catch (Exception e)
        {
            throw new RuntimeException("Recipe not found");
        }
    }

    //DELETE
    @Override
    public String deleteRecipeById(int id) {
        try
        {
        recipeRepository.deleteById(id);
        return "Recipe successfully deleted";
        }
        catch (Exception e)
        {
            throw new RuntimeException("Recipe not found");
        }
    }

    @Override
    public String deleteRecipeByName(String name) {
        try
        {
        recipeRepository.deleteRecipeByName(name);
        return "Recipe successfully deleted";
        }
        catch (Exception e)
        {
            throw new RuntimeException("Recipe not found");
        }
    }

    @Override
    public String addRecipePicture(String recipeName, MultipartFile recipePicture) {

        Recipe recipe=recipeRepository.findRecipeByName(recipeName);
        if(recipePicture == null) throw new RuntimeException("Recipe picture is null");
        String fileExtension = recipePicture.getOriginalFilename().substring(recipePicture.getOriginalFilename().lastIndexOf(".")+1);
        String fileName = recipeName + "." + fileExtension;
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path savingPath= Paths.get(currentPath.toString(), "src","main","resources","assets","RecipePictures", fileName);
        try
        {
            recipePicture.transferTo(savingPath);
            recipe.setRecipeUrl(savingPath.toString());
            System.out.println(savingPath.toString());
            recipeRepository.save(recipe);
            return "Recipe picture successfully added";
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while adding recipe picture, here are the details : " + e);
        }
    }

    @Override
    public ResponseEntity<FileSystemResource> getRecipePicture(String recipeName) {
        try
        {
            Recipe recipe = recipeRepository.findRecipeByName(recipeName);
            if (recipe == null) throw new RuntimeException("Recipe not found");
            File file = new File(recipe.getRecipeUrl());
            if (!file.exists()) throw new RuntimeException("Recipe picture not found");
            String fileExtension = recipe.getRecipeUrl().substring(recipe.getRecipeUrl().lastIndexOf(".") + 1);
            FileSystemResource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            if (fileExtension.equals("png"))
                headers.setContentType(MediaType.IMAGE_PNG); // Adjust the media type based on your image type
            if (fileExtension.equals("jpg") || fileExtension.equals("jpeg"))
                headers.setContentType(MediaType.IMAGE_JPEG);
            if (fileExtension.equals("gif")) headers.setContentType(MediaType.IMAGE_GIF);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while getting recipe picture, here are the details : " + e);
        }
    }

    @Override
    public String changeRecipePictureName(String recipeName, String newRecipeName) {
        try
        {
            Recipe recipe = recipeRepository.findRecipeByName(recipeName);
            if (recipe == null) throw new RuntimeException("Recipe not found");
            if (recipeRepository.findRecipeByName(newRecipeName) != null) throw new RuntimeException("Recipe picture name already exists");
            File file = new File(recipe.getRecipeUrl());
            if (!file.exists()) throw new RuntimeException("Recipe picture not found");
            String fileExtension = recipe.getRecipeUrl().substring(recipe.getRecipeUrl().lastIndexOf(".") + 1);
            file.renameTo(new File(recipe.getRecipeUrl().replace(recipeName, newRecipeName)));
            recipe.setRecipeUrl(recipe.getRecipeUrl().replace(recipeName, newRecipeName));
            recipeRepository.save(recipe);
            return "Recipe picture name successfully changed";
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while changing recipe picture name, here are the details : " + e);
        }
    }


}