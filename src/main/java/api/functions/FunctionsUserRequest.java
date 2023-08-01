package api.functions;

import api.models.request.UserLoginRequestModel;
import api.request.UserRequest;
import api.models.request.UserCreateRequestModel;

public class FunctionsUserRequest extends UserRequest {
    public String getUserCreate(String name, String email, String password, Integer code) {
        UserCreateRequestModel requestModel = new UserCreateRequestModel(name, email, password);
        return requestUserCreate(requestModel, code);
    }

    public String getUserLogin(String email, String password, Integer code){
        UserLoginRequestModel requestModel = new UserLoginRequestModel(email,password);
        return requestUserLogin(requestModel,code);
    }

    public static void getUserDelete(String token){
        if (token != null) {
            requestDelete(token);
        } else {
            requestDelete();
        }
    }
}