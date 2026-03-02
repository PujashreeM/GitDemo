package com.demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.Order;
import pojo.OrderDetails;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class EcommerceApiTest {
    public static void main(String[] args) {
        RequestSpecification rs = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest lr = new LoginRequest();
        lr.setUserEmail("pujashreemallick11@gmail.com");
        lr.setUserPassword("Puja@123");

        RequestSpecification rsl = given().log().all().spec(rs).body(lr);
        LoginResponse lrs = rsl.when().post("api/ecom/auth/login").then().log().all().extract().as(LoginResponse.class);
        System.out.println(lrs.getToken());
        String token = lrs.getToken();
        System.out.println(lrs.getUserId());
        String userId = lrs.getUserId();
        System.out.println(lrs.getMessage());

//        Add product
        RequestSpecification addProductBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token).build();
       RequestSpecification addProduct = given().log().all().spec(addProductBase).param("productName","Laptop")
               .param("productAddedBy",userId)
               .param("productCategory","fashion")
               .param("productSubCategory","shirts")
               .param("productPrice","11500")
               .param("productDescription","Addias Originals")
               .param("productFor","women")
               .multiPart("productImage", new File("/Users/pujashree/Desktop/Screenshot 2026-02-26 at 10.52.29 PM.png"));

               String addProdResponse = addProduct.when().post("/api/ecom/product/add-product")
               .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(addProdResponse);
        String productId = js.get("productId");
        System.out.println(productId);

//        Create Order
        RequestSpecification CreateOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).setContentType(ContentType.JSON).build();

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountry("India");
        orderDetails.setProductOrderedId(productId);

        List <OrderDetails> orderList = new ArrayList<>();
        orderList.add(orderDetails);
        Order order = new Order();
        order.setOrders(orderList);


       RequestSpecification createOrder = given().log().all().spec(CreateOrderBase).body(order);
       String addOrderResponse = createOrder.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
       System.out.println(addOrderResponse);

    RequestSpecification getOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
            .addHeader("Authorization",token).setContentType(ContentType.JSON).build();

   RequestSpecification getOrder = given().log().all().spec(getOrderBase);

           String getOrderResponse = getOrder.get("api/ecom/order/get-orders-details?id=69a041c30ab5a029774c73c6").then().log().all().extract().response().asString();
            System.out.println(getOrderResponse);


//            Delete order
        RequestSpecification deleteOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .addHeader("Authorization",token).setContentType(ContentType.JSON).build();
        RequestSpecification deleteRequest = given().log().all().spec(deleteOrderBase).pathParam("productOrderedId",productId);
        String deleteResponse = deleteRequest.when().delete("/api/ecom/product/delete-product/{productOrderedId}").then().log().all().extract().response().asString();


        JsonPath js1 = new JsonPath(deleteResponse);
        Assert.assertEquals("Product Deleted Successfully",js1.get("message"));



    }
}
