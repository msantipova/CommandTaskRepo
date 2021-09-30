package utils;

import static io.restassured.RestAssured.given;


public class TokenGenerator {

    /**
     * *
     * @param log login
     * @param pass password
     * @return token
     */
    public static String createToken(String log, String pass) {

        String requetsParams = "{" +
                "  \"password\": \"" + pass + "\"," +
                "  \"rememberMe\": true," +
                "  \"username\": \"" + log + "\"" +
                "}";

        return given()
                .body(requetsParams)
                .headers(
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*")
                .post("http://31.131.249.140:8080/api/authenticate")
                .then().
                        extract().
                        path("id_token").toString();
    }

}
