package entity;

/**
 * The representation of a recipe in our program.
 */
public interface Recipe {
    /**
     * Returns the unique identifier of this recipe.
     *
     * @return the unique identifier of this recipe.
     */
    int getRecipeId();

    /**
     * Returns the title of this recipe.
     *
     * @return the title of this recipe.
     */
    String getTitle();

    /**
     * Returns the image URL of this recipe.
     *
     * @return the image URL of this recipe.
     */
    String getImage();

}
