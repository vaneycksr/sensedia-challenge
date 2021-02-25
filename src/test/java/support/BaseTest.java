package support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;

public class BaseTest {

    private static final String CARD = "/cards";
    private static final String CARD_ID = "/cards/{id}";

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

        /**
         * COMENTEI ESSE TRECHO DE CÓDIGO POIS ALGUMAS RESPOSTAS DE REQUISIÇÕES QUANDO RETORNA
         * UM STATUS DE ERRO, NÃO ESTÃO SENDO RETORNADOS NO FORMATO JSON
         * */
        // valida se todas as requisições tem como resposta um JSON
        //RestAssured.responseSpecification = new ResponseSpecBuilder()
        //        .expectContentType(ContentType.JSON)
        //        .build();*/
    }

    public void removeCard(String idCard){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        given().
                pathParam("id",idCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("idList",credentials.getIdList()).
        when().
                delete(CARD_ID).
        then().
                statusCode(HttpStatus.SC_OK);
    }

    public String createCardAndReturnId(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        return given().
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("idList",credentials.getIdList()).
                queryParam("name","CARD BASE").
        when().
                post(CARD).
        then().
                statusCode(HttpStatus.SC_OK).
        extract().
                path("id");
    }

    public void editCard(String idCard){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        given().
                pathParam("id",idCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("name","EDITOOOU").
        when().
                put(CARD_ID).
        then().
                statusCode(HttpStatus.SC_OK).
                body("name", is("EDITOOOU"));
    }
}