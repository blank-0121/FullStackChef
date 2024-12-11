package entity;

import java.util.List;

/**
 * The representation of a common favorite collection in our program.
 */
public class CommonFavoriteCollection implements FavoriteCollection {
    private String name;
    private final List<Integer> favoriteCollections;

    public CommonFavoriteCollection (String name, List<Integer> favoriteCollections) {
        this.name = name;
        this.favoriteCollections = favoriteCollections;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Integer> getFavoriteCollections() {
        return favoriteCollections;
    }

}
