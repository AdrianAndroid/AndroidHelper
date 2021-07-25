public class UserModel {

    private String username;
    private String password;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int checkUserValidity(String username, String password) {
        if (username == null || password == null ||
                username.isEmpty() ||
                password.isEmpty()) {
            return -1;
        }
        return 0;
    }
}