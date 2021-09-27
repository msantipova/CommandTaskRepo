import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class AccountTest {

    @Test
    public void shouldRegisterAccount() {

        given().baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .contentType("application/json")
                .auth().basic("admin", "u7ljdajLNo7PsVw7")
                .when().get("/account").
                then().statusCode(200);
    }

}
