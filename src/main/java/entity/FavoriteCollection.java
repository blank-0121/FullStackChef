package entity;

import java.util.List;

/**
 * The representation of a favorite collection in our program.
 */
public interface FavoriteCollection {
    /**
     * Get the name of the favorite collection.
     * @return the name of the favorite collection
     */
    String getName();

    /**
     * Set the name of the favorite collection.
     * @param name the name of the favorite collection
     */
    void setName(String name);

    /**
     * Get the list of favorite collection IDs.
     * @return the list of favorite collection IDs
     */
    List<Integer> getFavoriteCollections();

}
