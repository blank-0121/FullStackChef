package interface_adapter.mealplan.update_meals;

import interface_adapter.ViewManagerModel;
import use_case.mealplan.generate_mealplan.WeeklyMealRecipeDto;
import use_case.mealplan.update_meals.UpdateMealsOutputBoundary;
import use_case.mealplan.update_meals.UpdateMealsOutputData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UpdateMealsPresenter implements UpdateMealsOutputBoundary {
    private UpdateMealsViewModel viewModel;
    private ViewManagerModel viewManagerModel;
    public UpdateMealsPresenter(UpdateMealsViewModel viewModel, ViewManagerModel viewManager) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManager;
    }
    public void prepareSuccessView(UpdateMealsOutputData outputData) {
        Map<LocalDate, List<WeeklyMealRecipeDto>> mealPlan = outputData.getMealPlan();
        final UpdateMealsState state = viewModel.getState();
        state.setMealPlan(mealPlan);
        viewModel.firePropertyChanged();

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void prepareFailView(String errorMessage) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
