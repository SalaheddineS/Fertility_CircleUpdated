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
    private final RecipeRepository recipeRepository;
    private final ImageService imageService;
    //CREATE
    @Override
    public Recipe addRecipe(Recipe recipe) {
        try
        {
            if (recipeRepository.findRecipeByName(recipe.getName()).isPresent()) throw new RuntimeException("Recipe already exists");

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
            return recipeRepository.findRecipeByName(name).orElseThrow(() -> new RuntimeException("Recipe not found"));
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
    public String assignImageToRecipe(String recipe, MultipartFile file) {
        return imageService.addRecipePicture(recipe, file);
    }

    @Override
    public String removeImageFromRecipe(String recipe) {
        return imageService.removeRecipePicture(recipe);
    }

    @Override
    public ResponseEntity<FileSystemResource> getImageRecipe(String recipe) {
        return imageService.getRecipePicture(recipe);
    }


}