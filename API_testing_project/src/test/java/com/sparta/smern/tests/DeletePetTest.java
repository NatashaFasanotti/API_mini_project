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
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;

public class DeletePetTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    public static Pet pet = new Pet(888, new Pet.Category(889, "Dogs"), "under_the_bed", List.of("stringOfPhotoUrl"),
            List.of(new Pet.Tag(886, "stringOfTag")), "available");
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


    @BeforeAll
    @DisplayName("Create test pet")
    public static void beforeAll() {
        // Build post request to create a test pet
        RequestSpecification postRequest = requestSpecBuilder()
                .setBasePath(PET_PATH)
                .build();
        // Send in the pet record
        postRequest.body(pet);

        sendRequest(postRequest);

    }

    @Test
    @DisplayName("Delete the newly created object")
    public void deletePetTest(){
        RequestSpecification deleteRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/" + pet.id())
                .build();

        Response result = RestAssured
                .given(deleteRequest)
                .when()
                .delete();

        MatcherAssert.assertThat(result.getStatusCode(), is(200));
    }

    @Test
    @DisplayName("Pet can not be retrieved")
    public void irretrievablePetTest(){
        RequestSpecification getPetRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/" + pet.id())
                .build();

        Response response = RestAssured
                .given(getPetRequest)
                .when()
                .get()
                .then()
                .extract().response();

        // 404 Pet Not Found
        MatcherAssert.assertThat(response.statusCode(), is(404));
    }

}
