package entity;

/**
 * The representation of a password-protected user for our program.
 */
public class CommonUser implements User {
    private final String name;
    private String password;

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

}
