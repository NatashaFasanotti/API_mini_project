package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.Category;
import com.sparta.smern.pojos.PetObject;
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

import java.util.Map;

import static org.hamcrest.Matchers.is;

public class DeletePetTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    private static final String PET_ID = "888";
    private static String jsonBody = """
            {
                "id": 888,
                "category": {
                    "id": 889,
                    "name": "Dogs"
                },
                "name": "under_the_bed",
                "photoUrls": [
                    "stringOfPhotoUrl"
                ],
                "tags": [
                    {
                        "id": 887,
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

    private static ResponseSpecification getJsonResponseWithStatus(Integer statusCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .build();
    }


    @BeforeAll
    @DisplayName("Create a pet with a JSON body")
    public static void createPetWithJsonBody() {
        // Define the JSON body as a String or use a Map or POJO that will be serialized to JSON
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeaders(Map.of(
                        "Accept", "application/json",
                        "Content-Type", "application/json"
                ))
                .setBasePath(BASE_PATH + PET_PATH)
                .build();

        PetObject petIdentifier = RestAssured
                .given(requestSpec)
                .body(jsonBody)
                .when()
                .post()
                .then()
                .spec(getJsonResponseWithStatus(200))
                .extract()
                .as(PetObject.class);

        MatcherAssert.assertThat(petIdentifier.getId(), is(888));
    }

    @Test
    @DisplayName("Delete the newly created object")
    public void deletePet(){
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ))
                .setBasePath(BASE_PATH + PET_PATH + "/" + PET_ID)
                .build();

        Response result = RestAssured
                .given(requestSpec)
                .when()
                .delete();

        MatcherAssert.assertThat(result.getStatusCode(), is(200));
    }

}
