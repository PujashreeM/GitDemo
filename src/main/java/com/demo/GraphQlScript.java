package com.demo;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GraphQlScript {
    public static void main(String[] args) {
//Query

     String response = given().log().all().header("content-type","application/json")
                .body("{\"query\":\"query($characterId:Int!,$episodeId:Int!)\\n{\\n  character(characterId:$characterId) \\n  {\\n    name\\n    gender\\n    status\\n    id\\n    type\\n  }\\n  location(locationId:8)\\n  {\\n    name\\n    dimension\\n  }\\n  episode(episodeId:$episodeId)\\n  {\\n    name\\n    air_date\\n    episode\\n  }\\n  \\n  characters(filters:{name:\\\"Rahul\\\"})\\n  {\\n    info\\n    {\\n      count\\n    }\\n    result\\n    {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters:{episode: \\\"hulu\\\"})\\n  {\\n    result\\n    {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n    \\n  }\\n}\\n\",\"variables\":{\"characterId\":8,\"episodeId\":1}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();
     System.out.println(response);
        JsonPath jp = new JsonPath(response);
        String characters = jp.getString("data.characters.name");
        Assert.assertEquals(characters,null);

//        Mutation

        String mutationResponse = given().log().all().header("content-type","application/json")
                .body("{\"query\":\"mutation($locationName:String!,$characterName:String!,$episodeName:String!)\\n{\\n  createLocation(location:{name:$locationName,type:\\\"SouthEast\\\",dimension:\\\"234\\\"})\\n  {\\n    id\\n  }\\n  createCharacter(character:{name:$characterName,type:\\\"macho\\\",status:\\\"dead\\\",species:\\\"fantacy\\\",gender:\\\"female\\\",image:\\\"png\\\",originId:15,locationId:15})\\n  {\\n    id\\n  }\\n  createEpisode(episode:{name:$episodeName,air_date:\\\"test\\\",episode:\\\"NetFlix\\\"})\\n  {\\n    id\\n  }\\n  deleteLocations(locationIds:[19612])\\n  {\\n    locationsDeleted\\n  }\\n}\",\"variables\":{\"locationName\":\"London\",\"characterName\":\"Robit\",\"episodeName\":\"Netflix\"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();
        System.out.println(mutationResponse);
        JsonPath jp1 = new JsonPath(mutationResponse);

    }
}
