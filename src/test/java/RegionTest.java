import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.DbConnection;
import utils.GeneratHttpRequest;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class RegionTest {

    Random random = new Random();

    @Test
    public void shouldCreateRegion() {
        String regionName = "Afrika_" + random.nextInt(100000000);
        String body = "{\n" +
                "  \"regionName\": \"" + regionName + "\"\n" +
                "}";
        Response response = given().spec(GeneratHttpRequest.bodyRequest(body)).post("/regions");
        if (response.statusCode() != 201) {
            assertFalse(true, "StatusCode is " + response.statusCode());
        } else {
            String responseRegionId = response.jsonPath().get("id").toString();
            assertAll(
                    () -> assertEquals((Integer) response.jsonPath().get("id"),
                            DbConnection.sqlSelectQuery("SELECT id FROM region WHERE region_name='" + regionName + "'").getInt("id"), "id не совпадают"),
                    () -> assertEquals(response.jsonPath().get("regionName"),
                            DbConnection.sqlSelectQuery("SELECT region_name FROM region WHERE id='" + responseRegionId + "'").getString("region_name"), "name не совпадают")
            );
        }
    }

    @Test
    public void shouldGetAllRegion() {

        Response response = given().spec(GeneratHttpRequest.noBodyRequest()).get("/regions");

        assertAll(
                () -> assertEquals(response.statusCode(), 200, "Метод упал"),
                () -> assertTrue(response.jsonPath().getList("$").size() > 0, "список вернулся пустым")
        );
    }
}
