package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.PetObject;
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

import static org.hamcrest.Matchers.is;

public class FindPetTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    public static final String TOKEN = ApiConfig.getToken();

    private ValidatableResponse setUpRequest(String path, Map<String, Object> pathParameters) {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + path)
                .addPathParam("pet_id", "10")
                .build();
        return RestAssured
                .given(requestSpec)
                .when()
                .log().all()
                .get()
                .then()
                .spec(getJsonResponseWithStatus(200))
                .log().all();
    }

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
        PetObject petIdentifier = setUpRequest("/{pet_id}",
                Map.of(
                        "pet_id", "10"
                ))
                .extract()
                .as(PetObject.class);

        MatcherAssert.assertThat(petIdentifier.getId(), is(10));
    }

    @Test
    @DisplayName("Check the category ID is correct")
    void checkCategory_Id() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
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

        MatcherAssert.assertThat(category.get("id"), is(3));
    }

    @Test
    @DisplayName("Check the category name is correct")
    void checkCategory_Name() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
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

        MatcherAssert.assertThat(category.get("name"), is("Rabbits"));
    }

    @Test
    @DisplayName("Check the name is correct")
    void checkName(){
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
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

        MatcherAssert.assertThat(petId, is("Rabbit 1"));
    }

    @Test
    @DisplayName("Check the status is correct")
    void checkStatus(){
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
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

        MatcherAssert.assertThat(petId, is("available"));
    }

}
