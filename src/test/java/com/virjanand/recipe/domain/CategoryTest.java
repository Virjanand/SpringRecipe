package com.virjanand.recipe.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest {

    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        Long idValue = 4L;
        category.setId(idValue);
        assertThat(category.getId()).isEqualTo(idValue);
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}
