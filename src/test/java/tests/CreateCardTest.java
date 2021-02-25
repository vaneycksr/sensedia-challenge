package tests;

import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;
import support.Credentials;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class CreateCardTest extends BaseTest {

    private static final String CARD = "/cards";

    @Test
    public void testCriarCardComSucesso(){

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
                body("id", is(notNullValue())).
                body("idList", is(notNullValue())).
                // validar name
        extract().
                path("id");

        removeCard(idCard);
    }

    @Test
    public void testCriarCardComTokenVazioKeyVazio(){

        Credentials credentials = new Credentials();

        credentials.setIdList("6034497434582f128505ef8a");

        given().
                queryParam("key","").
                queryParam("token","").
                queryParam("idList",credentials.getIdList()).
                queryParam("name","testinho 4").
        when().
                post(CARD).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid key"));
    }

    @Test
    public void testCriarCardComTokenNullKeyNull(){
        Credentials credentials = new Credentials();

        credentials.setToken(null);
        credentials.setKey(null);
        credentials.setIdList("6034497434582f128505ef8a");

        given().
                queryParam("key","").
                queryParam("token","").
                queryParam("idList",credentials.getIdList()).
                queryParam("name","testinho 4").
        when().
                post(CARD).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid key"));
    }

    @Test
    public void testCriarCardComTokenValidoKeyInvalido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b7648040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        given().
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("idList",credentials.getIdList()).
                queryParam("name","testinho 4").
        when().
                post(CARD).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid key"));
    }

    @Test
    public void testCriarCardComTokenInvalidoKeyValido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        given().
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("idList",credentials.getIdList()).
                queryParam("name","testinho 4").
        when().
                post(CARD).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid token"));
    }

    @Test
    public void testCriarCardComTokenInvalidoKeyInvalido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef57bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        given().
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("idList",credentials.getIdList()).
                queryParam("name","testinho 4").
        when().
                post(CARD).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid key"));
    }

    @Test
    public void testCriarCardComIdListInvalidoETokenEKeyValidos(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582505ef8a");

        given().
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("idList",credentials.getIdList()).
                queryParam("name","testinho 4").
        when().
                post(CARD).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body(containsString("invalid value for idList"));
    }
}
