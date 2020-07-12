import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import static io.restassured.path.json.JsonPath.*;

import io.restassured.RestAssured;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
//import io.restassured.RestAssured.*;

/**
 * test basic features
 */
public class RestAssuredCRUDTest {

    final String baseURI = "http://jsonplaceholder.typicode.com";

    /**
     * check Status Code
     * /
//     */
    @BeforeClass
    public void setUp() {
        RestAssured.config = new RestAssuredConfig().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.basePath = baseURI;
    }

    @Test
    public void testStatusCode_test()
    {
        given().get(baseURI+"/posts/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreateNewPost() throws Exception {
        given().
                formParam("title", "foo").
                formParam("body", "bodybar").
                formParam("userId", "1").
                when().
                post(baseURI+"/posts").
                then().
                statusCode(201).
                body("title", equalTo("foo")).body("id", anything());
    }

    @Test
    public void testUpdateExistedPost() throws Exception {
        Response response =
                given().
                        formParam("title", "foo").
                        when().
                        put(baseURI+"/posts/1");

        System.out.print(response.asString());
        response.then().
                body("title", equalTo("foo")).
                statusCode(200);
    }

    @Test
    public void testDeletePost() throws Exception {
        when().
                delete(baseURI+"/posts/2").
                then().
                statusCode(200);
    }

    @Test
    public void testReadComments() throws Exception {
        given().
                param("postId", 1).
                when().
                get(baseURI+"/comments").
                then().
                body(".", hasSize(5));
    }
}
