package com.demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SpecBuilderTest {
    public static void main(String[] args) {


        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setName("test");
        p.setAddress("29, side layout, cohen 09");
        p.setPhone_number("7976564323");
        p.setLanguage("French-IN");
        p.setWebsite("http://google.com");

        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        p.setType(myList);

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);


        RequestSpecification rs = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();

//        RestAssured.baseURI ="https://rahulshettyacademy.com";
        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();
        RequestSpecification res = given().spec(rs).queryParam("key","qaclick123").body(p)
                .contentType("application/json");

               Response response = res.when().post("/maps/api/place/add/json")
                .then().assertThat().spec(resSpec).extract().response();

        String resString = response.asString();
        System.out.println(resString);

    }
}
