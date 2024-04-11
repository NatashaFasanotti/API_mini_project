package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.Category;
import com.sparta.smern.pojos.PetObject;
import com.sparta.smern.records.Pet;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;


import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FindPetTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    private static final String PET_ID = "999";

    public static Pet pet = new Pet(999, new Pet.Category(998, "Dogs"), "under_the_bed", List.of("stringOfPhotoUrl"),
            List.of(new Pet.Tag(998, "stringOfTag")), "available");
    private static PetObject petObject;
    private static Category category;


        private static RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
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
        RestAssured
                .given(request)
                .when()
                .post()
                .then()
                .spec(getJsonResponseWithStatus(200));
    }

    public static void createPetPojo(RequestSpecification request){
        petObject =
                RestAssured
                    .given(request)
                    .when()
                        .get()
                    .then()
                        .spec(getJsonResponseWithStatus(200))
                        .extract()
                        .as(PetObject.class);
    }

    public static void createCategoryPojo (RequestSpecification request){
            category =
                    RestAssured
                            .given(request)
                            .when()
                            .get()
                            .then()
                            .spec(getJsonResponseWithStatus(200))
                            .extract()
                            .as(PetObject.class)
                            .getCategory();
    }

    @BeforeAll
    @DisplayName("Create a pet to Test")
    public static void beforeAll(){
        RequestSpecification postRequest = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH)
                .build();
        postRequest.body(pet);

        sendRequest(postRequest);

        RequestSpecification getPetObject = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
                .addPathParam("pet_id", PET_ID)
                .build();

        createPetPojo(getPetObject);
        createCategoryPojo(getPetObject);
    }

    @Test
    @DisplayName("Check the id is correct")
    void checkId(){
        MatcherAssert.assertThat(petObject.getId(), is(pet.id()));
    }

    @Test
    @DisplayName("Check the category ID is correct")
    void checkCategory_Id() {
        MatcherAssert.assertThat(category.getId(), is(pet.category().id()));
    }

    @Test
    @DisplayName("Check the category name is correct")
    void checkCategory_Name() {
        MatcherAssert.assertThat(category.getName(), is(pet.category().name()));
    }

    @Test
    @DisplayName("Check the name is correct")
    void checkName(){
        MatcherAssert.assertThat(petObject.getName(), is(pet.name()));
    }

    @Test
    @DisplayName("Check the status is correct")

    void checkStatus(){
        MatcherAssert.assertThat(petObject.getStatus(), is(pet.status()));
    }

    @AfterAll
    @DisplayName("Delete the newly created object - teardown")
    static void tearDown(){
        RequestSpecification deletePet = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + PET_PATH + "/{pet_id}")
                .addPathParam("pet_id", PET_ID)
                .build();

        RestAssured
                .given(deletePet)
                .when()
                .delete()
                .then()
                .spec(getJsonResponseWithStatus(200));


    }
}






