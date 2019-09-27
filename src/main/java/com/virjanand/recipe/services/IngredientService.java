package com.virjanand.recipe.services;

import com.virjanand.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long IngredientId);
}
