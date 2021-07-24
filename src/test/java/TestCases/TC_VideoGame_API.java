package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
public class TC_VideoGame_API {
	@Test(priority=1)
	public void test_getAllVideoGames() {
		given ()		
		.when()
			.get("http://localhost:8080/app/videogames")			
		.then()
			.statusCode(200);
	}
	
	@Test(priority=2)
	public void test_addNewVideoGame() {
		HashMap data=new HashMap();
		data.put("id","11");
		data.put("name","CarRace");
		data.put("releaseDate", "2021-07-21T06:09:21.955Z");
		data.put("reviewScore", 5);
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		Response res=
			given()
				.contentType("application/json")
				.body(data)
			.when()
				.post("http://localhost:8080/app/videogames")
			.then()
				.statusCode(200)
				.log().body()
				.extract().response();
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
	}
	
	@Test(priority=3)
	public void test_getVideoGame() {
		given()
    	.when()
    		.get("http://localhost:8080/app/videogames/11")
    		.then()
    			.statusCode(200)
    			.log().body()
    			.body("videoGame.id", equalTo("11"))
				.body("videoGame.name", equalTo("CarRace"));
	}
	
	@Test(priority=4)
	public void test_putVideoGame() {
		HashMap data=new HashMap();
		data.put("id","11");
		data.put("name","CarRace1");
		data.put("releaseDate", "2021-07-21T06:09:21.955Z");
		data.put("reviewScore", 4);
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		given()
			.contentType("application/json")
			.body(data)
		.when()
			.put("http://localhost:8080/app/videogames/11")
			.then()
				.statusCode(200)
				.log().body()
				.body("videoGame.id", equalTo("11"))
				.body("videoGame.name", equalTo("CarRace1"));				
	}
	
	@Test(priority=5)
	public void test_deleteVideoGame() {
		Response res=
		given()
		.when()
			.delete("http://localhost:8080/app/videogames/11")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonstring = res.asString();
		Assert.assertEquals(jsonstring.contains("Record Deleted Successfully"),true);
	}
}
