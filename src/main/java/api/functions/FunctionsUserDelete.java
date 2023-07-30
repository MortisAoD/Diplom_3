package api.functions;

import api.request.UserDelete;

public class FunctionsUserDelete extends UserDelete {
    public static void getUserDelete(String token){
        if (token != null) {
            requestDelete(token);
        } else {
            requestDelete();
        }
    }
}