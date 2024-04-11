package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.PetObject;
import com.sparta.smern.records.Pet;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.Matchers.is;

public class UpdatePetTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    public static Pet pet = new Pet(777, new Pet.Category(779, "Dogs"), "under_the_bed", List.of("stringOfPhotoUrl"),
            List.of(new Pet.Tag(776, "stringOfTag")), "available");
    private static ValidatableResponse result;
    private static PetObject testPet;

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
                .expectContentType(ContentType.JSON)
                .build();
    }

    private static void sendRequest(RequestSpecification request){
        result = RestAssured
                .given(request)
                .when()
                .post()
                .then()
                .spec(getJsonResponseWithStatus(200));
    }

    public static void createPojo(RequestSpecification request){
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
        // Build post request to create a test pet
        RequestSpecification postRequest = requestSpecBuilder()
                .setBasePath(PET_PATH)
                .build();
        // Send in the pet record
        postRequest.body(pet);

        // API Request 1 - POST request to create a test pet
        sendRequest(postRequest);

        // Build post request to update the test pet
        RequestSpecification updatePetRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/" + pet.id())
                .addQueryParams(Map.of(
                        "name", pet.name(),
                        "status", "sold"
                ))
                .build();

        // API Request 2 - POST request to UPDATE the pet details
        sendRequest(updatePetRequest);

        // API Request 3 - GET request to retrieve pet details
        RequestSpecification getPetRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/" + pet.id())
                .build();

        // create POJO for the test class to use
        createPojo(getPetRequest);
    }

    @Test
    @DisplayName("Correct ID Test")
    void correctIdTest(){
        MatcherAssert.assertThat(testPet.getId(), is(pet.id()));
    }

    @Test
    @DisplayName("Correct Name Test")
    public void correctNameTest() {
        MatcherAssert.assertThat(testPet.getName(), is(pet.name()));
    }

    @Test
    @DisplayName("Status has changed")
    public void statusChangedTest() {
        MatcherAssert.assertThat(testPet.getStatus(), is("sold"));
    }

}
