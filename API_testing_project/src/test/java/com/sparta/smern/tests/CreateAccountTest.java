package com.sparta.smern.tests;

import com.sparta.smern.ApiConfig;
import com.sparta.smern.pojos.AccountObject;
import com.sparta.smern.pojos.PetObject;
import com.sparta.smern.records.Account;
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

public class CreateAccountTest {

    public static final String BASE_URI = ApiConfig.getBaseUri();
    public static final String BASE_PATH = ApiConfig.getBasePath();
    public static final String PET_PATH = ApiConfig.getCommonBasePath();
    public static Account account = new Account(99, "testUser", "Mrs", "Tester", "example@example.com", "testPassword", "0123456789", 1);
    private static ValidatableResponse result;
    private static AccountObject testAccount;
}