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

import static org.hamcrest.Matchers.*;

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

        System.out.println("Approved: " + inventoryData.get("approved"));
        System.out.println("Placed: " + inventoryData.get("placed"));
        System.out.println("Delivered: " + inventoryData.get("delivered"));
    }

    @Test
    @DisplayName("Check approved returns a positive integer")
    void approvedReturnsPositiveInteger() {
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

        MatcherAssert.assertThat(inventoryData.get("approved"), greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Check placed returns a positive integer")
    void placedReturnsPositiveInteger() {
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

        MatcherAssert.assertThat(inventoryData.get("placed"), greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Check delivered returns a positive integer")
    void deliveredReturnsPositiveInteger() {
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

        MatcherAssert.assertThat(inventoryData.get("delivered"), greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Check that id field exists and is a number")
    void checksIdField(){
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + "/pet/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        Map<String, Object> petInfo =
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

        MatcherAssert.assertThat(petInfo.containsKey("id"), equalTo(true));
        MatcherAssert.assertThat(petInfo.get("id"), instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Check that id is a positive number")
    void checksIdIsPositive(){
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + "/pet/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        Integer petId =
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
                        .get("id");

        MatcherAssert.assertThat(petId, is(greaterThanOrEqualTo(0)));
    }
}


