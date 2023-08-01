package api.functions;

import com.google.gson.Gson;

public class Utilities {
    public static String toJsonString(Object modelClass) {
        Gson gson = new Gson();
        return gson.toJson(modelClass);
    }

    public static <T> T fromJsonString(String json, Class<T> modelClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, modelClass);
    }
}