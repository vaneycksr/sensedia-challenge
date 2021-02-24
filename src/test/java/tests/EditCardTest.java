package tests;

import com.sun.xml.bind.v2.TODO;
import org.apache.http.HttpStatus;
import org.junit.Test;
import support.BaseTest;
import support.Credentials;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class EditCardTest extends BaseTest {

    private static final String CARD = "/cards";
    private static final String CARD_ID = "/cards/{id}";

   /**TODO USAR O JAVA FAKER, E COLOCAR OS METODOS DE EDITAR E REMOVER NO BASETEST**/


    @Test
    public void testEditCardComSucesso(){

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

    public void editCard(String idCard){

        Credentials credentials = new Credentials();
        /**
         * DELETANDO CARD PELO ID
         * */
        credentials.setToken("6b282b1ca98e76ba4e4ef4b69957bd2504209bb63cba2d22e4b8c2116c611fb7");
        credentials.setKey("b76488a0cb0944040e4ac8d4aeaf41f7");
        credentials.setIdList("6034497434582f128505ef8a");

        given().
                pathParam("id",idCard).
                queryParam("key",credentials.getKey()).
                queryParam("token",credentials.getToken()).
                queryParam("idList",credentials.getIdList()).
                queryParam("name","EDITOOOU").
        when().
                put(CARD_ID).
        then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "name", is("EDITOOOU")
                );
    }

    public void removeCard(String idCard){

        Credentials credentials = new Credentials();
        /**
         * DELETANDO CARD PELO ID
         * */
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

}
