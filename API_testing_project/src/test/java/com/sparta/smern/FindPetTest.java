package com.sparta.smern;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FindPetTest {

    public static final String BASE_PATH = ApiConfig.getBaseUri();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    public static final String TOKEN = ApiConfig.getToken();

    private static RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_PATH)
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
    @DisplayName("Check the id is correct")
    void checkId() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        Integer petId =
                given(requestSpec)
                        .when()
                        .log().all()
                        .get()
                        .then()
                        .spec(getJsonResponseWithStatus(200))
                        .log().all()
                        .extract()
                        .jsonPath()
                        .get("id");

        MatcherAssert.assertThat(petId, is(10));
    }

    @Test
    @DisplayName("Check the category ID is correct")
    void checkCategory_Id() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        Map category =
                given(requestSpec)
                        .when()
                        .log().all()
                        .get()
                        .then()
                        .spec(getJsonResponseWithStatus(200))
                        .log().all()
                        .extract()
                        .jsonPath()
                        .get("category");

        MatcherAssert.assertThat(category.get("id"), is(3));
    }

    @Test
    @DisplayName("Check the category name is correct")
    void checkCategory_Name() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        Map<String, String> category =
                given(requestSpec)
                        .when()
                        .log().all()
                        .get()
                        .then()
                        .spec(getJsonResponseWithStatus(200))
                        .log().all()
                        .extract()
                        .jsonPath()
                        .get("category");

        MatcherAssert.assertThat(category.get("name"), is("Rabbits"));
    }

    @Test
    @DisplayName("Check the name is correct")
    void checkName() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        String petId =
                given(requestSpec)
                        .when()
                        .log().all()
                        .get()
                        .then()
                        .spec(getJsonResponseWithStatus(200))
                        .log().all()
                        .extract()
                        .jsonPath()
                        .get("name");

        MatcherAssert.assertThat(petId, is("Rabbit 1"));
    }

    @Test
    @DisplayName("Check the status is correct")
    void checkStatus() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        String petId =
                given(requestSpec)
                        .when()
                        .log().all()
                        .get()
                        .then()
                        .spec(getJsonResponseWithStatus(200))
                        .log().all()
                        .extract()
                        .jsonPath()
                        .get("status");

        MatcherAssert.assertThat(petId, is("available"));
    }
}






