package com.virjanand.recipe.services;

import com.virjanand.recipe.commands.RecipeCommand;
import com.virjanand.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
