package entity;

import java.util.List;

/**
 * Represents dietary restrictions selected by the user.
 */
public class CommonDietaryRestriction implements DietaryRestriction {
    private final List<String> dietaryRestrictions;

    public CommonDietaryRestriction(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

}
