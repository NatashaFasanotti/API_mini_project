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

public class FindPetTest {
    public static final String BASE_URI = "http://localhost:8080";
    public static final String BASE_PATH = "/api/v3";

    private static RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeaders(Map.of(
                "Accept", "application/json"
                ));
    }

    private static ResponseSpecification getJsonResponseWithStatus(Integer statusCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    @DisplayName("Check the id is correct")
    void checkId(){
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

        MatcherAssert.assertThat(petId, is(10));
    }

    @Test
    @DisplayName("Check the category ID is correct")
    void checkCategory_Id() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + "/pet/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        Map category =
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
                        .get("category");

        MatcherAssert.assertThat(category.get("id"), is(1));
    }

    @Test
    @DisplayName("Check the category ID is correct")
    void checkCategory_Name() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + "/pet/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        Map <String, String> category =
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
                        .get("category");

        MatcherAssert.assertThat(category.get("name"), is("Dogs"));
    }

    @Test
    @DisplayName("Check the id is correct")
    void checkName(){
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + "/pet/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        String petId =
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
                        .get("name");

        MatcherAssert.assertThat(petId, is("whiskers"));
    }

    @Test
    @DisplayName("Check the id is correct")
    void checkStatus(){
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + "/pet/{pet_id}")
                .addPathParam("pet_id", "10")
                .build();
        String petId =
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
                        .get("status");

        MatcherAssert.assertThat(petId, is("sold"));
    }





}
