package com.virjanand.recipe;

import com.virjanand.recipe.domain.*;
import com.virjanand.recipe.repositories.CategoryRepository;
import com.virjanand.recipe.repositories.RecipeRepository;
import com.virjanand.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        log.debug("Creating initial recipes to bootstrap database");

        //get UOMs
        UnitOfMeasure eachUom = unitOfMeasureRepository.findByDescription("Each").orElseThrow(this::createExceptionUomNotFound);
        UnitOfMeasure tableSpoonUom = unitOfMeasureRepository.findByDescription("Tablespoon").orElseThrow(this::createExceptionUomNotFound);
        UnitOfMeasure teaSpoonUom = unitOfMeasureRepository.findByDescription("Teaspoon").orElseThrow(this::createExceptionUomNotFound);
        UnitOfMeasure dashUom = unitOfMeasureRepository.findByDescription("Dash").orElseThrow(this::createExceptionUomNotFound);
        UnitOfMeasure pintUom = unitOfMeasureRepository.findByDescription("Pint").orElseThrow(this::createExceptionUomNotFound);
        UnitOfMeasure cupUom = unitOfMeasureRepository.findByDescription("Cup").orElseThrow(this::createExceptionUomNotFound);

        Category americanCategory = categoryRepository.findByDescription("American").orElseThrow(() -> new RuntimeException("Expected Category Not Found"));
        Category mexicanCategory = categoryRepository.findByDescription("Mexican").orElseThrow(() -> new RuntimeException("Expected Category Not Found"));

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh" + "\n" + "2 Mash with a fork");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("for a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment!");
        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("salt", new BigDecimal(0.5), teaSpoonUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(guacRecipe);
        return recipes;
    }

    private RuntimeException createExceptionUomNotFound() {
        return new RuntimeException("Expected UOM Not Found");
    }
}
