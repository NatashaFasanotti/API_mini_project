package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.AccountObject;
import com.sparta.smern.records.Account;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.Matchers.is;

public class CreateAccountTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String USER_PATH = ApiConfig.getUserPath();
    public static Account account = new Account(99, "testUser", "Mrs", "Tester", "example@example.com", "testPassword", "0123456789", 1);
    private static ValidatableResponse result;
    private static AccountObject testAccount;

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

    private static void sendRequest(RequestSpecification request) {
        result = RestAssured
                .given(request)
                .when()
                .post()
                .then()
                .spec(getJsonResponseWithStatus(200));
    }

    public static void createPojo(RequestSpecification request) {
        testAccount = RestAssured
                .given(request)
                .when()
                .get()
                .then()
                .spec(getJsonResponseWithStatus(200))
                .extract()
                .as(AccountObject.class);
    }

    @BeforeAll
    @DisplayName("Create, Update & Get test pet")
    public static void beforeAll() {
        // Build post request to create a test account
        RequestSpecification postRequest = requestSpecBuilder()
                .setBasePath(USER_PATH)
                .build();
        // Send in the account record
        postRequest.body(account);

        // API Request 1 - POST request to create a test account
        sendRequest(postRequest);

        // API Request 2 - GET request to retrieve account details
        RequestSpecification getAccountRequest = requestSpecBuilder()
                .setBasePath(USER_PATH + "/" + account.username())
                .build();

        // create POJO for the test class to use
        createPojo(getAccountRequest);
    }

    @Test
    @DisplayName("Correct ID Test")
    void correctIdTest() {
        MatcherAssert.assertThat(testAccount.getId(), is(account.id())); // may need to recast the result as an int
    }

    @Test
    @DisplayName("Correct firstName")
    void correctFirstNameTest() {
        MatcherAssert.assertThat(testAccount.getFirstName(), is(account.firstName())); // may need to recast the result as an int
    }

    @Test
    @DisplayName("Correct last name")
    void correctLastNameTest() {
        MatcherAssert.assertThat(testAccount.getLastName(), is(account.lastName())); // may need to recast the result as an int
    }

    @Test
    @DisplayName("Correct email")
    void correctEmailTest() {
        MatcherAssert.assertThat(testAccount.getEmail(), is(account.email())); // may need to recast the result as an int
    }

    @Test
    @DisplayName("Correct password")
    void correctPasswordTest() {
        MatcherAssert.assertThat(testAccount.getPassword(), is(account.password())); // may need to recast the result as an int
    }

    @Test
    @DisplayName("Correct phone")
    void correctPhoneTest() {
        MatcherAssert.assertThat(testAccount.getPhone(), is(account.phone())); // may need to recast the result as an int
    }

    @Test
    @DisplayName("Correct user status")
    void correctUserStatusTest() {
        MatcherAssert.assertThat(testAccount.getUserStatus(), is(account.userStatus())); // may need to recast the result as an int
    }

    @Test
    @DisplayName("Add account with empty fields")
    void emptyFieldsAccountTest() {
        Account invalidAccount = new Account(7, "", "", "", "", "", "", 2);
        RequestSpecification postRequest = requestSpecBuilder()
                .setBasePath(USER_PATH)
                .build();
        postRequest.body(invalidAccount);
        result = RestAssured
                .given(postRequest)
                .when()
                .post()
                .then()
                .spec(getJsonResponseWithStatus(200));
    }

    @AfterAll
    @DisplayName("Delete Test Account after all")
    public static void afterAll() {
        RequestSpecification deleteRequest = requestSpecBuilder()
                .setBasePath(USER_PATH + "/" + account.username())
                .build();

        result = RestAssured
                .given(deleteRequest)
                .when()
                .delete()
                .then();

    }

}