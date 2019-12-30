package restApi;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(SerenityRunner.class)
public class WhenRegisteringANewClient {

    @Before
    public void setBaseURI(){
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    public void each_new_client_should_be_given_a_unique_id()
    {
/*        String newClient = "{\n" +
                "  \"email\": \"RestAPI16@Serenity16.com\",\n" +
                "  \"firstName\": \"RestAPI16\",\n" +
                "  \"lastName\": \"Serenity16\"\n" +
                "}";*/

            Client newClient = Client.ClientBuilder.aClient()
                                .withFirstName("RestAPI20")
                                .withLastName("Serenity20")
                                .withEmail("RestAPI20@Serenity20.com")
                                .build();

                given().contentType("application/json")
                        .and().body(newClient)
                        .when().post("/client")
                        .then().statusCode(200)
                        .and().body("id",not(equalTo(0)));

                String clientId = SerenityRest.lastResponse().jsonPath().getString("id");
                String firstName = SerenityRest.lastResponse().jsonPath().getString("firstName");
                String lastName = SerenityRest.lastResponse().jsonPath().getString("lastName");
                String email = SerenityRest.lastResponse().jsonPath().getString("email");

                given()
                        .pathParam("id", clientId)
                .when()
                        .get("/client/{id}");

                Ensure.that("Status code is 200", response -> response.statusCode(200))
                        .andThat("FirstName is "+firstName, response-> response.body("firstName",equalTo(firstName)))
                        .andThat("LastName is "+lastName, response-> response.body("lastName",equalTo(lastName)))
                        .andThat("Email is "+email, response-> response.body("email",equalTo(email)));

    }
}
