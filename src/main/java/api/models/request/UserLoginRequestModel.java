package api.models.request;

public class UserLoginRequestModel {
    public String email;
    public String password;

    public UserLoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}