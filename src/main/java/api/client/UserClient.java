package api.client;

import io.restassured.response.Response;
import api.User;

import static io.restassured.RestAssured.given;
import static model.constants.UrlPath.*;

public class UserClient {

    public Response createUser(User user) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(user)
                .post(CREATE_USER_ENDPOINT);
    }

    public void deleteUser(String bearerToken){
        given().baseUri(BASE_URL).header("Authorization", bearerToken).log().all()
                .delete("/api/auth/user").then().log().all();
    }

    public Response loginUser(User user){
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(user)
                .post(LOGIN_USER_ENDPOINT);
    }

    public String getBearerToken(Response response){
        return response.then().extract().jsonPath().getString("accessToken");
    }

    public String getRefreshToken(Response response){
        return response.then().extract().jsonPath().getString("refreshToken");
    }
}
