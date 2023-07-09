package com.POC.AuthSecurity.Repositories;

import com.POC.AuthSecurity.Entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findRecipeByName(String name);
    void deleteRecipeByName(String name);

}
