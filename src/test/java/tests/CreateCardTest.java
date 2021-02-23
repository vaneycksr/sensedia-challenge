package tests;

import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;

import static io.restassured.RestAssured.given;

public class CreateCardTest extends BaseTest {

    private static final String CARD = "/cards";
    private static final String token = "6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7";
    private static final String key = "b76488a0cb0944040e4ac8d4aeaf41f7";
    private static final String ID_LIST = "6034497434582f128505ef8a";

    @Test
    public void testCriarCardComSucesso(){

        given().
                queryParam("key",key).
                queryParam("token",token).
                queryParam("idList",ID_LIST).
                queryParam("name","testinho").

        when().
                post(CARD).
         then().
                statusCode(HttpStatus.SC_OK);


    }

}
