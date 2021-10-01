import org.junit.jupiter.api.Test;
import utils.DbConnection;
import utils.TokenGenerator;

import java.sql.ResultSet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AccountTest {

    @Test
    public void shouldGetAccount() {
        given().baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .headers("Authorization", "Bearer " + TokenGenerator.createToken("admin", "u7ljdajLNo7PsVw7"),
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*")
                .when().get("/account")
                .then().statusCode(200)
                .body("login", equalTo("admin"));
    }

    @Test
    public void shouldRegisterAccount() throws Exception {

        String body = "{\n" +
                "  \"activated\": true,\n" +
                "  \"authorities\": [\n" +
                "    \"str\"\n" +
                "  ],\n" +
                "  \"createdBy\": \"string\",\n" +
                "  \"createdDate\": \"2021-09-27T16:51:40.254Z\",\n" +
                "  \"email\": \"string99@mail.ru\",\n" +
                "  \"firstName\": \"string\",\n" +
                "  \"id\": 88,\n" +
                "  \"imageUrl\": \"string\",\n" +
                "  \"langKey\": \"string\",\n" +
                "  \"lastModifiedBy\": \"string\",\n" +
                "  \"lastModifiedDate\": \"2021-09-27T16:51:40.254Z\",\n" +
                "  \"lastName\": \"string\",\n" +
                "  \"login\": \"string99\",\n" +
                "  \"password\": \"1234\"\n" +
                "}";

        given().baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .body(body)
                .headers("Authorization", "Bearer " + TokenGenerator.createToken("admin", "u7ljdajLNo7PsVw7"),
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*")
                .when().post("/register")
                .then().statusCode(201);
        ResultSet log = DbConnection.sqlSelectQuery("SELECT * FROM jhi_user WHERE login='string88'");
        if (log.getRow() != 0) {
            assertEquals("string88", log.getString("login"));
        } else assertTrue(false);
    }

}
