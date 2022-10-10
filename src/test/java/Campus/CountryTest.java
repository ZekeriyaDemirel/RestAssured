package Campus;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.*;

public class CountryTest {

    Cookies cookies;

    @BeforeClass
    public void loginCampus()
    {
     baseURI = "https://demo.mersys.io/";

     // {"username": "richfield.edu","password": "Richfield2020!","rememberMe": "true"}

        Map<String,String> credential=new HashMap<>();
        credential.put("username","richfield.edu");
        credential.put("password","Richfield2020!");
        credential.put("rememberMe","true");

      cookies=

        given()
                .contentType(ContentType.JSON)
                .body(credential)

                .when()
                .post("auth/login")

                .then()
                .log().all()
                .statusCode(200)
                .extract().response().getDetailedCookies()

        ;

    }

    @Test
    public void createCountry()
    {
given()
        .cookies(cookies)
        .log().all()


        .when()



        .then()


;


    }


}
