package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.PetObject;
import com.sparta.smern.records.Pet;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.Matchers.is;


import java.util.Collections;
import java.util.Map;

public class UpdatePetInvalidTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    private static ValidatableResponse result;
    private static PetObject testPet;

    public static Pet invalidPet = new Pet(-1, new Pet.Category(-1, ""), "under_the_bed", List.of("stringOfPhotoUrl"),
            List.of(new Pet.Tag(776, "stringOfTag")), "available");

    private static RequestSpecBuilder requestSpecBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI + BASE_PATH)
                .addHeaders(Map.of(
                        "Accept", "application/json",
                        "Content-Type", "application/json"
                ));
    }

    private static ResponseSpecification getJsonResponseWithStatus(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType("application/json")
                .build();
    }

    private static void sendRequest(RequestSpecification request) {
        result = RestAssured
                .given(request)
                .when()
                .post()
                .then()
                .spec(getJsonResponseWithStatus(200));
    }

    public static void createPojo(RequestSpecification request) {
        testPet = RestAssured
                .given(request)
                .when()
                .get()
                .then()
                .spec(getJsonResponseWithStatus(200))
                .extract()
                .as(PetObject.class);
    }

    @BeforeAll
    @DisplayName("Create, Update & Get test pet")
    public static void beforeAll() {

        RequestSpecification postRequest = requestSpecBuilder()
                .setBasePath(PET_PATH)
                .build();

        postRequest.body(invalidPet);

        RequestSpecification updateRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/" + invalidPet.id())
                .addQueryParams(Map.of(
                        "name", "Updated Name",
                        "status", "sold"
                ))
                .build();
        sendRequest(updateRequest);

        RequestSpecification getRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/" + invalidPet.id())
                .build();
        createPojo(getRequest);
    }

    @Test
    @DisplayName("Invalid Pet Update Test - Sad Path")
    public void invalidPetUpdateTest() {
        //update request fails with a status code indicating failure
        MatcherAssert.assertThat(result.extract().statusCode(), is(404));
    }

}



