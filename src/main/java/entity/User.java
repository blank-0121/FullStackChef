package entity;

/**
 * The representation of a password-protected user for our program.
 */
public interface User {
    /**
     * Get the name of the user.
     * @return the name of the user
     */
    String getName();

    /**
     * Get the password of the user.
     * @return the password of the user
     */
    String getPassword();

    /**
     * Set the password of the user.
     * @param password the new password
     */
    void setPassword(String password);

}
