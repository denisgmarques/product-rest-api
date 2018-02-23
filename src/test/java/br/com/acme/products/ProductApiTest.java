package br.com.acme.products;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = br.com.acme.products.Configuration.class)
public class ProductApiTest {
	
	@LocalServerPort
	int port;

	@Test
	public void test001_productInsertUpdate() throws InterruptedException, JSONException {

		//////////////////////////////////////////
		// Post kit and get generated product json #1
		//////////////////////////////////////////
		JSONObject kit = new JSONObject()
				.put("description", "This Kit is Essential")
				.put("name", "Kit Mouse Razer Chroma + Mouse Pad");

		Response response = given().port(port).contentType("application/json")
		.body(kit.toString())
		.when()
			.post("/api/products")
		.then()
			.statusCode(200)
		.extract().response();
		
		JSONObject kitJson = new JSONObject(response.getBody().asString());

		////////////////////////////
		// Post Mouse as a kit child #2
		////////////////////////////
		JSONObject mouse = new JSONObject()
				.put("description", "This mouse is amazing")
				.put("name", "Mouse Razer Chroma")
				.put("parent", kitJson);
		
		given().port(port).contentType("application/json")
		.body(mouse.toString())
		.when()
			.post("/api/products")
		.then()
			.statusCode(200);

		////////////////////////////////
		// Post Mouse Pad as a kit child #3
		////////////////////////////////
		JSONObject mousePad = new JSONObject()
				.put("description", "This mouse pad is amazing")
				.put("name", "Mouse pad gamer")
				.put("parent", kitJson);
		
		given().port(port).contentType("application/json")
		.body(mousePad.toString())
		.when()
			.post("/api/products")
		.then()
			.statusCode(200);
		
		/////////////////////////////////
		// Post a product to delete after #4
		/////////////////////////////////
		JSONObject anyProd = new JSONObject()
				.put("description", "This product is unuseful")
				.put("name", "To be deleted");
		
		response = given().port(port).contentType("application/json")
		.body(anyProd.toString())
		.when()
			.post("/api/products")
		.then()
			.statusCode(200)
		.extract().response();
		
		// Put parent in last inserted product (Update Product)
		anyProd = new JSONObject(response.body().asString());
		anyProd.put("parent", kitJson);
		
		given().port(port).contentType("application/json")
		.body(anyProd.toString())
		.when()
			.put("/api/products/4")
		.then()
			.statusCode(200);
		
	}

	@Test
	public void test002_productListAll() {

		given().port(port).contentType("application/json")
		.when()
			.get("/api/products")
		.then()
			.statusCode(200)
			.and()
			.body("size()", equalTo(4));
		
	}
	
	@Test
	public void test003_productListAllChildFromKit() {

		Response response = given().port(port).contentType("application/json")
		.when()
			.get("/api/products/1/childs")
		.then()
			.statusCode(200)
			.and()
			.body("size()", equalTo(3))
		.extract().response();
		
		System.out.println(response.body().toString());
	}	

	@Test
	public void test4_getFirstProduct() throws JSONException {

		given().port(port).contentType("application/json")
		.when()
			.get("/api/products/1")
		.then()
			.statusCode(200);
		
	}
	
	@Test
	public void test005_deleteProduct() throws JSONException {

		// Delete
		given().port(port).contentType("application/json")
		.when()
			.delete("/api/products/4")
		.then()
			.statusCode(200);
		
		// Check count
		given().port(port).contentType("application/json")
		.when()
			.get("/api/products")
		.then()
			.statusCode(200)
			.and()
			.body("size()", equalTo(3));
		
	}
	
	@Test
	public void test006_imageInsertUpdate() throws InterruptedException, JSONException {

		//////////////////////////////////////////
		// Post kit product image json #1
		//////////////////////////////////////////
		JSONObject kit = new JSONObject()
				.put("id", 1);
		
		JSONObject img1 = new JSONObject()
				.put("type", "Front view")
				.put("product", kit);
		
		JSONObject img2 = new JSONObject()
				.put("type", "Front view")
				.put("product", kit);
		
		JSONObject img3 = new JSONObject()
				.put("type", "Front view")
				.put("product", kit);

		given().port(port).contentType("application/json")
		.body(img1.toString())
		.when()
			.post("/api/images")
		.then()
			.statusCode(200);

		given().port(port).contentType("application/json")
		.body(img2.toString())
		.when()
			.post("/api/images")
		.then()
			.statusCode(200);

		given().port(port).contentType("application/json")
		.body(img3.toString())
		.when()
			.post("/api/images")
		.then()
			.statusCode(200);

	}
	
	@Test
	public void test007_imageListAll() {

		given().port(port).contentType("application/json")
		.when()
			.get("/api/images")
		.then()
			.statusCode(200)
			.and()
			.body("size()", equalTo(3));
		
	}
	
	@Test
	public void test008_imagesFromProduct() {

		given().port(port).contentType("application/json")
		.when()
			.get("/api/products/1/images")
		.then()
			.statusCode(200)
			.and()
			.body("size()", equalTo(3));
		
	}
	
	@Test
	public void test009_imageUpdate() throws JSONException {
		
		JSONObject mouse = new JSONObject()
				.put("id", 2);
		
		JSONObject img1 = new JSONObject()
				.put("id", 1)
				.put("type", "Backside view")
				.put("product", mouse);

		given().port(port).contentType("application/json")
		.body(img1.toString())
		.when()
			.put("/api/images/1")
		.then()
			.statusCode(200);
		
	}
	
	@Test
	public void test010_imagesFromProductAfterUpdate() {

		given().port(port).contentType("application/json")
		.when()
			.get("/api/products/1/images")
		.then()
			.statusCode(200)
			.and()
			.body("size()", equalTo(2));
		
		given().port(port).contentType("application/json")
		.when()
			.get("/api/products/2/images")
		.then()
			.statusCode(200)
			.and()
			.body("size()", equalTo(1));
		
	}
	
	@Test
	public void test011_deleteImage() throws JSONException {

		// Delete
		given().port(port).contentType("application/json")
		.when()
			.delete("/api/images/1")
		.then()
			.statusCode(200);
		
		// Check count
		given().port(port).contentType("application/json")
		.when()
			.get("/api/images")
		.then()
			.statusCode(200)
			.and()
			.body("size()", equalTo(2));
		
	}
}
