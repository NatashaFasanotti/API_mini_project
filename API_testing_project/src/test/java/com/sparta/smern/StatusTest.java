package com.sparta.smern;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isOneOf;

public class StatusTest {
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    public static final String BASE_URI = ApiConfig.getBaseUri();

    public static final String TOKEN = ApiConfig.getToken();

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
    @DisplayName("Retrieve list of available pets successfully")
    void retrieveAvailablePetsSuccessfully() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH +  "/findByStatus")
                .addQueryParam("status", "available")
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ))
                .build();
        ValidatableResponse response =
                given(requestSpec)
                        .when()
                        .log().all()
                        .get()
                        .then()
                        .spec(getJsonResponseWithStatus(200))
                        .log().all();
        response.body("size()", greaterThan(0)); // Ensure the list is not empty
        response.body("status", everyItem(is("available")));
    }

    @Test
    @DisplayName("Retrieve a list of available pets when PetStore API is unavailable")
    void testPetStoreUnavailable() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_PATH)
                .setContentType(ContentType.JSON)
                .build();

        given()
                .spec(requestSpec)
                .when()
                .get( PET_PATH + "/findByStatus")
                .then()
                .statusCode(anyOf(is(500), is(503)));
    }
}





