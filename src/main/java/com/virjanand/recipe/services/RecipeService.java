package com.virjanand.recipe.services;

import com.virjanand.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
