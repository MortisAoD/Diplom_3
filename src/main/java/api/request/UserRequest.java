package api.request;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRequest extends Request{
    public String requestUserCreate(Object model, Integer code) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post("/auth/register");
        response.then().statusCode(code);
        return response.getBody().asString();
    }

    public String requestUserLogin(Object model, Integer code) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(model)
                .when()
                .post("/auth/login");
        response.then().statusCode(code);
        return response.getBody().asString();
    }

    public String requestUserLogin() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .post("/auth/login");
        response.then().statusCode(401);
        return response.getBody().asString();
    }

    public static void requestDelete(String token) {
        given().auth()
                .oauth2(token.replace("Bearer ", ""))
                .header("Content-type", "application/json")
                .when()
                .delete("/auth/user")
                .then().statusCode(202);
    }

    public static void requestDelete() {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete("/auth/user")
                .then().statusCode(401);
    }
}