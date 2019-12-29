package iex;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class WhenFetchingRestApiInfo {

    @Before
    public void setBaseURIL(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void validate_response_statusCode()
    {
        given().pathParam("todo_id",1)
        .when().get("/todos/{todo_id}")
        .then().statusCode(200);
    }

    @Test
    public void validate_response_with_pathParam()
    {
        given().pathParam("todo_id",1)
        .when().get("/todos/{todo_id}");
        Ensure.that("title is returned", response -> response.body("title", equalTo("delectus aut autem")));
        Ensure.that("userId is returned", response -> response.body("userId", equalTo(1)));

    }

    @Test
    public void validate_response_with_queryParam()
    {
//        when().get("/users?email=Sincere@april.biz");
        given().queryParam("email","Sincere@april.biz").
        when().get("/users");

        Ensure.that("Name is returned", response -> response.body("name[0]", equalTo("Leanne Graham")))
        .andThat("username is returned", response -> response.body("username[0]", equalTo("Bret")))
        .andThat("email is returned", response -> response.body("email[0]", equalTo("Sincere@april.biz")));
    }

    @Test
    public void validate_response_with_jsonPath()
    {
        given().pathParam("todo_id",1)
                .when().get("/todos/{todo_id}");

        String title = lastResponse().jsonPath().getString("title");
        assertThat(title).isEqualTo("delectus aut autem");
    }

    @Test
    public void validate_response_with_jsonPath_returns_list()
    {
        when().get("/todos");

        List<Integer> title_ids = lastResponse().jsonPath().getList("id");
        assertThat(title_ids.size()).isGreaterThan(1);
        assertThat(title_ids.size()).isLessThanOrEqualTo(200);
        assertThat(title_ids.size()).isEqualTo(200);
    }

    @Test
    public void validate_response_with_jsonPath_returns_mapValues()
    {
        given().queryParam("email","Sincere@april.biz").
                when().get("/users");

            String companyName = lastResponse().jsonPath().getString("company[0].name");
            String companyCatchPhrase = lastResponse().jsonPath().getString("company[0].catchPhrase");
            String companyBs = lastResponse().jsonPath().getString("company[0].bs");

            assertThat(companyName).isEqualTo("Romaguera-Crona");
            assertThat(companyCatchPhrase).isEqualTo("Multi-layered client-server neural-net");
            assertThat(companyBs).isEqualTo("harness real-time e-markets");
    }
}
