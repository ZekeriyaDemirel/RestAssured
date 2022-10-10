package GoRest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.*;

public class GoRestUsersTests {

    @BeforeClass
    void Setup()
    {
        baseURI="https://gorest.co.in/public/v2/";

    }

    public String getRandomName(){

        return RandomStringUtils.randomAlphabetic(8);
    }

    public String getRandomEmail(){

        return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }

    int userID=0;
    @Test
    public void createUserObject()
    {
        User newUser=new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");

        userID=
                given()
                        // api metoduna gitmeden önceki hazırlıklar : token, gidecek body, parametreleri
                        .header("Authorization","Bearer 830edb86151d39f411475d342629e7e5521e8112e0cff3ec63f523edac3af5fc")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                     //   .extract().path("id")
                        .extract().jsonPath().getInt("id")
                ;

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.

        System.out.println("userID = " + userID);
    }



    @Test(dependsOnMethods = "createUserObject",priority = 1)
    public void updateUserObject()
    {
       Map<String ,String> updateUser=new HashMap<>();
       updateUser.put("name","Zeki Demir");

                given()
                        // api metoduna gitmeden önceki hazırlıklar : token, gidecek body, parametreleri
                        .header("Authorization","Bearer 830edb86151d39f411475d342629e7e5521e8112e0cff3ec63f523edac3af5fc")
                        .contentType(ContentType.JSON)
                        .body(updateUser)
                        .log().body()
                        .pathParam("userID",userID)

                        .when()
                        .put("users/{userID}")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("name",equalTo("Zeki Demir"))

                ;

    }


    @Test(dependsOnMethods = "createUserObject",priority = 2)
    public void getUserByID()
    {
               given()
                .header("Authorization","Bearer 830edb86151d39f411475d342629e7e5521e8112e0cff3ec63f523edac3af5fc")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID",userID)

                .when()
                .get("users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))

        ;

    }

    @Test(dependsOnMethods = "createUserObject",priority = 3)
    public void deleteUserById()
    {
        given()
                .header("Authorization","Bearer 830edb86151d39f411475d342629e7e5521e8112e0cff3ec63f523edac3af5fc")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID",userID)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(204)
        ;

    }

    @Test(dependsOnMethods = "deleteUserById")
    public void deleteUserByIdNegative()
    {
        given()
                .header("Authorization","Bearer 830edb86151d39f411475d342629e7e5521e8112e0cff3ec63f523edac3af5fc")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID",userID)

                .when()
                .delete("users/{userID}")

                .then()
                .log().body()
                .statusCode(404)
        ;

    }


    @Test
    public void getUsers()
    {
        Response response=

        given()
                .header("Authorization","Bearer 830edb86151d39f411475d342629e7e5521e8112e0cff3ec63f523edac3af5fc")

                .when()
                .get("users")

                .then()
                .log().body()
                .statusCode(200)
                .extract().response();


        // TODO : 3 usersın id sini alınız (path ve jsonPath ile ayrı ayrı yapınız)
        // TODO : Tüm gelen veriyi bir nesneye atınız  (google araştırması)
        // TODO : GetUserByID testinde dönen user ı bir nesneye atınız.

    }




    @Test(enabled = false)
    public void createUser()
    {
        int userID=
                given()
                        // api metoduna gitmeden önceki hazırlıklar : token, gidecek body, parametreleri
                        .header("Authorization","Bearer 830edb86151d39f411475d342629e7e5521e8112e0cff3ec63f523edac3af5fc")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\""+getRandomName()+"\", \"gender\":\"male\", \"email\":\""+getRandomEmail()+"\", \"status\":\"active\"}")

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
                ;
        System.out.println("userID = " + userID);
    }




    @Test(enabled = false)
    public void createUserMap()
    {
        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",getRandomName());
        newUser.put("gender","male");
        newUser.put("email",getRandomEmail());
        newUser.put("status","active");

        int userID=
                given()
                        // api metoduna gitmeden önceki hazırlıklar : token, gidecek body, parametreleri
                        .header("Authorization","Bearer 830edb86151d39f411475d342629e7e5521e8112e0cff3ec63f523edac3af5fc")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .log().body()

                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
                ;
        System.out.println("userID = " + userID);
    }

}



class User{

    private String name;
    private String  gender;
    private String email;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


