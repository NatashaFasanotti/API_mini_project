package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.Category;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class AddPetTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();

    public static Pet newpet = new Pet(987, new Pet.Category(988, "Dogs"), "hiding_in_the_wardrobe", List.of("stringOfPhotoUrl"),
            List.of(new Pet.Tag(989, "stringOfTag")), "available");
    public static Pet emptypet = new Pet(0, new Pet.Category(0, ""), "", List.of(""),
            List.of(new Pet.Tag(0, "")), "");

    private static PetObject testNewPet;
    private static Category testNewPetCategory;

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
        ValidatableResponse result = RestAssured
                .given(request)
                .when()
                .post()
                .then()
                .spec(getJsonResponseWithStatus(200));
    }

    public static void createPojo(RequestSpecification request){
        testNewPet = RestAssured
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
        postRequest.body(newpet);

        // API Request 1 - POST request to create a test pet
        sendRequest(postRequest);

        // API Request 2 - GET request to retrieve pet details
        RequestSpecification getPetRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/" + newpet.id())
                .build();

        // create POJO for the test class to use
        createPojo(getPetRequest);
    }

    @Test
    @DisplayName("Correct Pet ID Test")
    void correctIdTest(){
        MatcherAssert.assertThat(testNewPet.getId(), is(newpet.id()));
    }

    @Test
    @DisplayName("Correct Name Test")
    public void correctNameTest() {
        MatcherAssert.assertThat(testNewPet.getName(), is(newpet.name()));
    }

    @Test
    @DisplayName("Correct Category Test")
    public void statusChangedTest() {
        testNewPetCategory = testNewPet.getCategory();
        MatcherAssert.assertThat(testNewPetCategory.getName(), is("Dogs"));
    }

    @Test
    @DisplayName("New Pet appears in the list of 'available' pets")
    public void appearsInListTest() {
        // API Request - GET request to retrieve List of Available Pets
        RequestSpecification getPetsRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/findByStatus")
                .addQueryParam("status", "available")
                .build();

        List<PetObject> resultAvailablePets = RestAssured
                .given(getPetsRequest)
                .when()
                .get()
                .then()
                .spec(getJsonResponseWithStatus(200))
                .extract()
                .jsonPath()
                .getList(".", PetObject.class);

        List<String> availablePets = new ArrayList<>();
        for (PetObject animal: resultAvailablePets) {
            availablePets.add(animal.getName());
            // System.out.println(animal.getName());
        }

        MatcherAssert.assertThat(testNewPet.getName(), availablePets.contains(testNewPet.getName()));
    }

    @Test
    @DisplayName("New Pet does not appear in the list of 'sold' pets")
    public void doesNotAppearInListTest() {
        // API Request - GET request to retrieve List of Available Pets
        RequestSpecification getPetsRequest = requestSpecBuilder()
                .setBasePath(PET_PATH + "/findByStatus")
                .addQueryParam("status", "sold")
                .build();

        List<PetObject> resultSoldPets = RestAssured
                .given(getPetsRequest)
                .when()
                .get()
                .then()
                .spec(getJsonResponseWithStatus(200))
                .extract()
                .jsonPath()
                .getList(".", PetObject.class);

        List<String> soldPets = new ArrayList<>();
        for (PetObject animal: resultSoldPets) {
            soldPets.add(animal.getName());
            // System.out.println(animal.getName());
        }

        MatcherAssert.assertThat(testNewPet.getName(), not(soldPets.contains(testNewPet.getName())));
    }

    @Test
    @DisplayName("Sad Path - Add an empty pet")
    public void addEmptyPet() {
        RequestSpecification postRequest = requestSpecBuilder()
                .setBasePath(PET_PATH)
                .build();
        postRequest.body(emptypet);

        Response response = RestAssured
                .given(postRequest)
                .when()
                .post();

        MatcherAssert.assertThat(response.statusCode(), is(200)); // it adds an empty pet!
    }

}
