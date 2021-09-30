package GenerationTestData;

import io.restassured.response.ValidatableResponse;
import utils.TokenGenerator;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class GenerationRegion {
    public static int InsertRegion() {
        Random random = new Random();
        String regionName = "Afrika_" + random.nextInt(100000);
        String body = "{\n" +
                "  \"regionName\": \"" + regionName + "\"\n" +
                "}";

        ValidatableResponse response = given().baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .body(body)
                .headers("Authorization", "Bearer " + TokenGenerator.createToken("admin", "u7ljdajLNo7PsVw7"),
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*")
                .when().post("/regions")
                .then().statusCode(201);
        return response.extract().jsonPath().get("id");
    }
}
