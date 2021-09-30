import GenerationTestData.GenerationRegion;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import utils.DbConnection;
import utils.TokenGenerator;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegionTest {

    @Test
    public void shouldCreateRegion() throws Exception {
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
        int regionId = response.extract().jsonPath().get("id");
        int regionDbId = DbConnection.sqlQuery("SELECT id FROM region WHERE region_name='" + regionName + "'").getInt("id");
        assertEquals(regionId, regionDbId);
    }

    @Test
    public void shouldGetAllRegion() throws Exception {
        int regionId = GenerationRegion.InsertRegion();
        ValidatableResponse response = given().baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .headers("Authorization", "Bearer " + TokenGenerator.createToken("admin", "u7ljdajLNo7PsVw7"),
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*")
                .when().get("/regions")
                .then().statusCode(200);

        //int regionResponseId = response.extract().jsonPath().get("id");
//        int regionDbId = DbConnection.sqlQuery("SELECT id FROM region WHERE region_name='" + regionName + "'").getInt("id");
//        assertEquals(regionId, regionDbId);
    }
}
