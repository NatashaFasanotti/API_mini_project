package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.Category;
import com.sparta.smern.pojos.PetObject;
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

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FindPetTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    public static final String TOKEN = ApiConfig.getToken();

    private static final String PET_ID = "999";
    private static String jsonBody = """
            {
                "id": 999,
                "category": {
                    "id": 998,
                    "name": "Dogs"
                },
                "name": "under_the_bed",
                "photoUrls": [
                    "stringOfPhotoUrl"
                ],
                "tags": [
                    {
                        "id": 997,
                        "name": "stringOfTag"
                    }
                ],
                "status": "available"
            }
           """;

    private ValidatableResponse setUpRequest(String path, Map<String, Object> pathParameters) {
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


    @BeforeAll
    @DisplayName("Create a pet with a JSON body")
    static void createPetWithJsonBody() {
        // Define the JSON body as a String or use a Map or POJO that will be serialized to JSON
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeaders(Map.of(
                        "Accept", "application/json",
                        "Content-Type", "application/json"
                ))
                .setBasePath(BASE_PATH + PET_PATH)
                .build();

        RestAssured
                .given(requestSpec)
                    .body(jsonBody)
                .when()
                    .post()
                .then()
                    .spec(getJsonResponseWithStatus(200));
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


        MatcherAssert.assertThat(petIdentifier.getId(), is(999));

    }

    @Test
    @DisplayName("Check the category ID is correct")
    void checkCategory_Id() {

        Category categoryIdentifier = setUpRequest("/{pet_id}",
                Map.of(
                        "pet_id", PET_ID
                ))
                .extract()
                .as(PetObject.class)
                .getCategory();
           MatcherAssert.assertThat(categoryIdentifier.getId(), is(998));

    }

    @Test
    @DisplayName("Check the category name is correct")
    void checkCategory_Name() {

        Category categoryIdentifier = setUpRequest("/{pet_id}",
                Map.of(
                        "pet_id", PET_ID
                ))
                .extract()
                .as(PetObject.class)
                .getCategory();
        MatcherAssert.assertThat(categoryIdentifier.getName(), is("Dogs"));

    }

    @Test
    @DisplayName("Check the name is correct")

    void checkName(){
        PetObject petIdentifier = setUpRequest("/{pet_id}",
                Map.of(
                        "pet_id", PET_ID
                ))
                .extract()
                .as(PetObject.class);

        MatcherAssert.assertThat(petIdentifier.getName(), is("under_the_bed"));

    }

    @Test
    @DisplayName("Check the status is correct")

    void checkStatus(){
        PetObject petIdentifier = setUpRequest("/{pet_id}",
                Map.of(
                        "pet_id", PET_ID
                ))
                .extract()
                .as(PetObject.class);

        MatcherAssert.assertThat(petIdentifier.getStatus(), is("available"));
    }

    @AfterAll
    @DisplayName("Delete the newly created object - teardown")
    static void tearDown(){
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ))
                .setBasePath(BASE_PATH + PET_PATH + "/" + PET_ID)
                .build();

        RestAssured
                .given(requestSpec)
                .when()
                .delete()
                .then()
                .spec(getJsonResponseWithStatus(200));


    }
}






