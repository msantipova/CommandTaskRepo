import GenerationTestData.GenerationRegion;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import utils.DbConnection;
import utils.GeneratHttpRequest;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RegionTest {

    @Test
    public void shouldCreateRegion() throws SQLException {
        //generat randome regionName
        String regionName = GenerationRegion.generateRegionName();
        //create new region
        String body = "{\"regionName\": \"" + regionName + "\"" + "}";
        // send request
        Response response = given().spec(GeneratHttpRequest.bodyRequest(body)).post("/regions");
        //assert statusCode
        if (response.statusCode() != 201) {
            fail("StatusCode is " + response.statusCode());
        } else {
            int responseRegionId = response.jsonPath().get("id");
            //asserts content
            assertAll(
                    () -> assertEquals(responseRegionId,
                            DbConnection.sqlSelectQuery("SELECT id FROM region WHERE region_name='" + regionName + "'").getInt("id"), "id не совпадают"),
                    () -> assertEquals(response.jsonPath().get("regionName"),
                            DbConnection.sqlSelectQuery("SELECT region_name FROM region WHERE id='" + responseRegionId + "'").getString("region_name"), "name не совпадают")
            );
            //clean testData
            DbConnection.sqlDeleteRegion(responseRegionId);
        }
    }

    @Test
    public void shouldGetAllRegions() throws SQLException {
        //generat randome regionName
        String regionName = GenerationRegion.generateRegionName();
        //insert new region
        int responseRegionId = GenerationRegion.InsertRegion(regionName);
        //send request, out response
        Response response = given().spec(GeneratHttpRequest.noBodyRequest()).get("/regions");
        //asserts content
        assertAll(
                () -> assertEquals(response.statusCode(), 200, "Метод упал"),
                () -> assertTrue(response.jsonPath().getList("$").size() > 0, "список вернулся пустым")
        );
        //clean testData
        DbConnection.sqlDeleteRegion(responseRegionId);
    }

    @Test
    public void shouldGetRegion() throws SQLException {
        //generat randome regionName
        String regionName = GenerationRegion.generateRegionName();
        //insert new region
        int regionId = GenerationRegion.InsertRegion(regionName);
        //send request, out response
        Response response = given().spec(GeneratHttpRequest.noBodyRequest()).get("/regions/" + regionId);
        //assert statusCode
        if (response.statusCode() != 200) {
            fail("StatusCode is " + response.statusCode());
        } else {
            int responseRegionId = response.jsonPath().get("id");
            //asserts content
            assertAll(
                    () -> assertEquals(responseRegionId, regionId, "id не совпадают"),
                    () -> assertEquals(response.jsonPath().get("regionName"), regionName, "name не совпадают")
            );
            //clean testData
            DbConnection.sqlDeleteRegion(responseRegionId);
        }
    }
}
