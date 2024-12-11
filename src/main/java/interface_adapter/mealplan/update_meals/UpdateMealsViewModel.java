package interface_adapter.mealplan.update_meals;

import app.Constants;
import interface_adapter.ViewModel;


public class UpdateMealsViewModel extends ViewModel<UpdateMealsState> {
    public UpdateMealsViewModel() {
        super(Constants.MEAL_PLAN_VIEW);
        setState(new UpdateMealsState());
    }
}
