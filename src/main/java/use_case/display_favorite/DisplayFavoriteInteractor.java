package use_case.display_favorite;

import app.SessionUser;
import entity.Recipe;
import entity.User;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The interactor for the Display Favorite Use Case.
 */
public class DisplayFavoriteInteractor implements DisplayFavoriteInputBoundary {

    private final DisplayFavoriteDataAccessInterface dataAccess;
    private final DisplayFavoriteOutputBoundary presenter;


    public DisplayFavoriteInteractor(DisplayFavoriteDataAccessInterface dataAccess,
                                     DisplayFavoriteOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        User user = SessionUser.getInstance().getUser();

        if (user == null) {
            presenter.prepareFailView("Error with retrieving user");
            return;
        }

        final List<Integer> favorites = dataAccess.getFavorites(user);

        StringBuilder recipeIds = new StringBuilder();
        for (int favorite : favorites) {
            recipeIds.append(favorite).append(",");
        }

        if (recipeIds.length() > 0) {
            recipeIds.setLength(recipeIds.length() - 1);
        }

        String recipeList = recipeIds.toString();

        try {
            List<Recipe> recipes = dataAccess.getRecipes(recipeList);
            final List<DisplayFavoriteOutputData> outputData = recipeToCommonRecipeOutputData(recipes);
            presenter.prepareSuccessView(outputData);
        }
        catch (IOException | JSONException ex) {
            presenter.prepareFailView("Failed to get recipes.");
        }

    }

    @Override
    public void switchToSearchView() {
        presenter.switchToSearchView();
    }

    private List<DisplayFavoriteOutputData> recipeToCommonRecipeOutputData(List<Recipe> recipes) {
        final List<DisplayFavoriteOutputData> outputData = new ArrayList<>();
        for (Recipe recipe : recipes) {
            outputData.add(new DisplayFavoriteOutputData(recipe.getRecipeId(), recipe.getTitle(), recipe.getImage()));
        }
        return outputData;
    }
}
