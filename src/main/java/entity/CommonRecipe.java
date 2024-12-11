package entity;

import java.util.List;

/**
 * The representation of a recipe in our program.
 */
public class CommonRecipe implements Recipe {
    private final int recipeId;
    private final String title;
    private final String image;

    public CommonRecipe(int recipeId, String title, String image) {
        this.recipeId = recipeId;
        this.title = title;
        this.image = image;
    }

    @Override
    public int getRecipeId() {
        return recipeId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImage() {
        return image;
    }

}
