package tests;

import org.apache.http.HttpStatus;
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import support.BaseTest;
import support.Credentials;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "DeleteCardTest.csv")
public class DeleteCardTest extends BaseTest {

    private static final String CARD_ID = "/cards/{id}";

    @Test
    public void testDeletarCardComSucesso(@Param(name="token")String token,
                                          @Param(name="key")String key){

        Credentials credentials = new Credentials();

        credentials.setToken(token);
        credentials.setKey(key);

        String idFirstCard = createCardAndReturnId();

        given().
                pathParam("id",idFirstCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
        when().
                delete(CARD_ID).
        then().
                statusCode(HttpStatus.SC_OK).
                body("limits",is(notNullValue()));
    }

    @Test
    public void testDeletarCardComIdInvalido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");

        given().
                pathParam("id","1111111111xxxxxxx").
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
        when().
                delete(CARD_ID).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body(containsString("invalid id"));
    }

    @Test
    public void testDeletarCardComTokenInvalido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef42d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");

        String idFirstCard = createCardAndReturnId();

        given().
                pathParam("id",idFirstCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
        when().
                delete(CARD_ID).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid token"));

        removeCard(idFirstCard);
    }

    @Test
    public void testDeletarCardComKeyInvalido(){

        Credentials credentials = new Credentials();

        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040ef7");

        String idFirstCard = createCardAndReturnId();

        given().
                pathParam("id",idFirstCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
        when().
                delete(CARD_ID).
        then().
                statusCode(HttpStatus.SC_UNAUTHORIZED).
                body(containsString("invalid key"));

        removeCard(idFirstCard);
    }
}