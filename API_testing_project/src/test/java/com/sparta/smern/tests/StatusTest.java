package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isOneOf;

public class StatusTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();

    private static RequestSpecBuilder baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH + PET_PATH + "/findByStatus")
                .addHeaders(Map.of(
                        "Accept", "application/json",
                        "Content-Type", "application/json"
                ));
    }

    private static ResponseSpecification jsonResponseSpec(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .build();
    }

    private static ValidatableResponse executeGetRequest() {
        RequestSpecification requestSpec = baseRequestSpec()
                .addQueryParam("status", "available")
                .build();

        return RestAssured
                .given(requestSpec)
                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .spec(jsonResponseSpec(200)); // Assuming default expected status is 200
    }

    @Test
    @DisplayName("Retrieve list of available pets successfully")
    void retrieveAvailablePetsSuccessfully() {
        ValidatableResponse response = executeGetRequest();

        response.body("size()", greaterThan(0));
        response.body("status", everyItem(is("available")));
    }

}






