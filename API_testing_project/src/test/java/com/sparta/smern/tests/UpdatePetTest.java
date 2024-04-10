package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.PetObject;
import com.sparta.smern.records.Pet;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
    private static final int PET_ID = pet.id();

    private static ResponseSpecification getJsonResponseWithStatus(Integer statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @BeforeAll
    @DisplayName("Create a pet with a JSON body")
    public static void createPetWithJsonBody() {
        // Build the headers and URL
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeaders(Map.of(
                        "Accept", "application/json",
                        "Content-Type", "application/json"
                ))
                .setBasePath(BASE_PATH + PET_PATH)
                .build();
        // Create the pet using the record
        requestSpec.body(pet);
        // POST the pet and receive the pet info back as a response and check it has status of 200
        ValidatableResponse result = RestAssured
                .given(requestSpec)
                .when()
                    .post()
                .then()
                    .spec(getJsonResponseWithStatus(200));

        // send a new api request to retrieve the pet again as a POJO
        PetObject newPet = setUpRequest("/{pet_id}",
                Map.of(
                        "pet_id", PET_ID
                ))
                .extract()
                .as(PetObject.class);

        MatcherAssert.assertThat(petIdentifier.getId(), is(PET_ID));
    }

    private static RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ));
    }

    private static ValidatableResponse setUpRequest(String path, Map<String, Object> pathParameters) {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + path)
                .addPathParam("pet_id", PET_ID)
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

    @Test
    @DisplayName("Check the id is correct")
    void checkId(){
        PetObject petIdentifier = setUpRequest("/{pet_id}",
                Map.of(
                        "pet_id", PET_ID
                ))
                .extract()
                .as(PetObject.class);
        MatcherAssert.assertThat(petIdentifier.getId(), is(PET_ID)); // may need to recast the result as an int
    }

}
