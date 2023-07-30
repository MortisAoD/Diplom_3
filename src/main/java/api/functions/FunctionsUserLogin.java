package api.functions;

import api.request.UserLogin;
import api.models.request.UserLoginRequestModel;

public class FunctionsUserLogin extends UserLogin {
    public String getUserLogin(String email, String password, Integer code){
        UserLoginRequestModel requestModel = new UserLoginRequestModel(email,password);
        return requestUserLogin(requestModel,code);
    }
}