package api.models.response;

public class UserModel {
    public String name;
    public String email;

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }
}