package com.POC.AuthSecurity.Repositories;

import com.POC.AuthSecurity.Entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findRecipeByName(String name);
    void deleteRecipeByName(String name);

}
