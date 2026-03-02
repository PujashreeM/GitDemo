package com.demo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializeTest {
    public static void main(String[] args) {


        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setName("test");
        p.setAddress("29, side layout, cohen 09");
        p.setPhone_number("7976564323");
        p.setLanguage("French-IN");
        p.setWebsite("http://google.com");

        List <String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        p.setType(myList);

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);

         RestAssured.baseURI ="https://rahulshettyacademy.com";
         Response res = given().queryParam("key","qaclick123").body(p)
                .contentType("application/json")
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();

      String resString = res.asString();
      System.out.println(resString);

    }
}
