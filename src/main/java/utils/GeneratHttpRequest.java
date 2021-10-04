package utils;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GeneratHttpRequest {

    public static RequestSpecification bodyRequest(String body) {
        return given()
                .baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .body(body)
                .headers("Authorization", "Bearer " + TokenGenerator.createToken("admin", "u7ljdajLNo7PsVw7"),
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*");
    }

    public static RequestSpecification noBodyRequest() {
        return given()
                .baseUri("http://31.131.249.140")
                .port(8080)
                .basePath("/api")
                .headers("Authorization", "Bearer " + TokenGenerator.createToken("admin", "u7ljdajLNo7PsVw7"),
                        "Content-Type",
                        "application/json",
                        "Accept",
                        "*/*");
    }

}
