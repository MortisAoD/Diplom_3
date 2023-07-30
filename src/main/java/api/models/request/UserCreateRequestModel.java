package api.models.request;

public class UserCreateRequestModel {
    public String name;
    public String email;
    public String password;

    public UserCreateRequestModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}