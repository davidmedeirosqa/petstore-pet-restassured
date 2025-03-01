import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class TestUser {
    String ct = "application/json"; // content-type
    String uriUser = "https://petstore.swagger.io/v2/user";
    String token;

    @Test 

    public void testLogin(){
        
        // Configuração
        // Variáveis de entrada
        String username = "David";
        String password = "123456";
        String expectedResult = "logged in user session:";

        Response response = (Response) given()
            .contentType(ct)
            .log().all()

        // Execução
            .when()
            .get(uriUser + "/login?username=" + username + "&password="+ password)
        
        // Validação
            .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200)) // Apagou
            .body("type", is("unknown")) // Desconhecido
            .body("message", containsString(expectedResult)) // Contém
            .body("message", hasLength(36)) // Contagem dos caracteres no campo "message"
            .extract()                  
        ;

        // Extração
        token =  response.jsonPath().getString("message").substring(23);
        System.out.println("Token: " + token);
    }
} 