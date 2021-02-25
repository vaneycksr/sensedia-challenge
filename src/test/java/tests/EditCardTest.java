package tests;

import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;
import support.Credentials;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class EditCardTest extends BaseTest {

    private static final String CARD = "/cards";
    private static final String CARD_ID = "/cards/{id}";

   /**TODO USAR O JAVA FAKER**/

    @Test
    public void testEditarEExcluirCardComSucesso(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        String idCard = given().
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("idList",credentials.getIdList()).
                queryParam("name","testinho 4").
        when().
                post(CARD).
        then().
                statusCode(HttpStatus.SC_OK).
        extract().
                path("id");

        editCard(idCard);
        removeCard(idCard);
    }

    @Test
    public void testEditarCardComTokenInvalido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b2822d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");

        String idFirstCard = createCardAndReturnId();

        given().
                pathParam("id",idFirstCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("name","EDITOOOU").
        when().
                put(CARD_ID).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid token"));

        removeCard(idFirstCard);
    }

    @Test
    public void testEditarCardComKeyInvalido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb09af41f7");

        String idFirstCard = createCardAndReturnId();

        given().
                pathParam("id",idFirstCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("name","EDITOOOU").
        when().
                put(CARD_ID).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid key"));

        removeCard(idFirstCard);
    }

    @Test
    public void testEditarCardComIdInvalido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");

        String idFirstCard = createCardAndReturnId();

        given().
                pathParam("id","11111111111111xx").
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("name","EDITOOOU").
        when().
                put(CARD_ID).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body(containsString("invalid id"));

        removeCard(idFirstCard);
    }

    @Test
    public void testMudarCardDeIdList(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        String idFirstCard = createCardAndReturnId();

        given().
                pathParam("id",idFirstCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("name","EDITOOOU").
                queryParam("idList","60345422f6dba5126d556b2e").
        when().
                put(CARD_ID).
        then().
                statusCode(HttpStatus.SC_OK).
                body("name", is("EDITOOOU")).
                body("idList", is("60345422f6dba5126d556b2e"));

        removeCard(idFirstCard);
    }
}
