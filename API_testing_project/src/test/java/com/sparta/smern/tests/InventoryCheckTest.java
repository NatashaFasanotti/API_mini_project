package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.InventoryObject;
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

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class InventoryCheckTest {
    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();

    public static final String INVENTORY_PATH = "/store/inventory";
    private static InventoryObject inventory;
//    private static Map<String, Integer> inventoryData;

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

    @BeforeAll
    @DisplayName("Inventory check")
    static void getInventoryCount() {
        RequestSpecification requestSpec = getRequestSpecBuilder()
                .setBasePath(BASE_PATH + INVENTORY_PATH)
                .build();
        inventory = (InventoryObject) given(requestSpec)
                .when()
                .log().all()
                .get()
                .then()
                .spec(getJsonResponseWithStatus(200))
                .log().all()
                .extract()
                .as(InventoryObject.class);
    }

    @Test
    @DisplayName("Check inventory count")
    void inventoryCount() {
        System.out.println("Approved: " + inventory.getApproved());
        System.out.println("Placed: " + inventory.getPlaced());
        System.out.println("Delivered: " + inventory.getDelivered());
    }

    @Test
    @DisplayName("Check approved returns a positive integer")
    void approvedReturnsPositiveInteger() {
        MatcherAssert.assertThat(inventory.getApproved(), greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Check placed returns a positive integer")
    void placedReturnsPositiveInteger() {
        MatcherAssert.assertThat(inventory.getPlaced(), greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Check delivered returns a positive integer")
    void deliveredReturnsPositiveInteger() {
        MatcherAssert.assertThat(inventory.getDelivered(), greaterThanOrEqualTo(0));
    }


}



