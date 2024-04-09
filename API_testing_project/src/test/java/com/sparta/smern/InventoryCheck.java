package com.sparta.smern;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.is;
public class InventoryCheck {
    public static final String BASE_URI = "http://localhost:8080";
    public static final String BASE_PATH = "/api/v3";

    private static RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ));
    }

    private static ResponseSpecification getJsonResponseWithStatus(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    @DisplayName("Check inventory count")
    void inventoryCountTest() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + "/store/inventory")
                .build();

        Map<String, Integer> inventoryData =
                RestAssured
                        .given(requestSpec)
                        .when()
                            .log().all()
                            .get()
                        .then()
                            .spec(getJsonResponseWithStatus(200))
                            .log().all()
                            .extract()
                            .jsonPath()
                            .getMap("");

        // matcher

    }
}


