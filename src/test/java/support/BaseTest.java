package support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.*;

public class BaseTest {

    @BeforeClass
    public static void setUp(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // configura para todos os testes o endpoint
        baseURI = "https://api.trello.com";

        // configura o path para todos os testes
        basePath = "/1";

        // define que todas as requisições que vão ser enviadas são do tipo JSON
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

        // valida se todas as requisições tem como resposta um JSON
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }


}
