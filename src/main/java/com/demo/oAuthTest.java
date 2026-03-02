package com.demo;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuthTest {
    public static void main(String[] args) {
        String [] arr = {"Selenium Webdriver Java","Cypress","Protractor"};

      String response =  given()
                .formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

      System.out.println(response);

      JsonPath jsonPath = new JsonPath(response);
      String accessToken = jsonPath.getString("access_token");

      GetCourse courses = given()
              .queryParam("access_token",accessToken)
              .when().log().all()
              .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
              .as(GetCourse.class);

      System.out.println(courses.getLinkedIn());
      System.out.println(courses.getInstructors());
      System.out.println(courses.getCourses().getApi().get(1).getCourseTitle());

      List<Api> apiCourses = courses.getCourses().getApi();
      for(int i=0; i<apiCourses.size(); i++){
          if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
              System.out.println(apiCourses.get(i).getPrice());
          }
      }
      ArrayList <String> al = new ArrayList<String>();

     List<WebAutomation> w = courses.getCourses().getWebAutomation();
      for (int i=0; i<w.size(); i++){
         al.add(w.get(i).getCourseTitle());
      }
      List<String> expectedList = Arrays.asList(arr);
      Assert.assertTrue(al.equals(expectedList));

    }
}
