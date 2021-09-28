import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class AccountTest {

    public String createToken(String log, String pass){

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

    @Test
    public void shouldGetAccount() {
        given().baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .headers("Authorization", "Bearer " + createToken("admin", "u7ljdajLNo7PsVw7"),
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*")
                .when().get("/account")
                .then().statusCode(200)
                .body("login", equalTo("admin"));
    }

    @Test
    public void shouldRegisterAccount() {
        String body = "{\n" +
                "  \"activated\": true,\n" +
                "  \"authorities\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"createdBy\": \"string\",\n" +
                "  \"createdDate\": \"2021-09-27T16:51:40.254Z\",\n" +
                "  \"email\": \"string@mail.ru\",\n" +
                "  \"firstName\": \"string\",\n" +
                "  \"id\": 0,\n" +
                "  \"imageUrl\": \"string\",\n" +
                "  \"langKey\": \"string\",\n" +
                "  \"lastModifiedBy\": \"string\",\n" +
                "  \"lastModifiedDate\": \"2021-09-27T16:51:40.254Z\",\n" +
                "  \"lastName\": \"string\",\n" +
                "  \"login\": \"string1\",\n" +
                "  \"password\": \"string\"\n" +
                "}";

        given().baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .body(body)
                .headers("Authorization", "Bearer " + createToken("admin", "u7ljdajLNo7PsVw7"),
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*")
                .when().post("/register")
                .then().statusCode(201);
    }

}
