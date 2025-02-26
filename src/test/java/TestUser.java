// 1- Nome do pacote

// 2- Bibliotecas
import io.restassured.response.Response; // Classe resposta do Rest-assured

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given; // Função given
import static org.hamcrest.Matchers.*; // Classe de comparadores do Hamcrest

// 3- Classe
public class TestUser {

    // 3.1- Atributos
    static String ct = "application/json"; // content-type
    static String uriPet = "https://petstore.swagger.io/v2/pet"; // Base URL + Endpoint (user)

    // 3.2 - Funções e métodos
    // 3.2.1 - Funções e métodos comuns/úteis

    // Função de leitura do JSON
    public static String readFileJson(String fileJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileJson)));
    }

    // 3.2.2 - Métodos de testes
    @Test
    public void testPostPet() throws IOException {

        // Configura
        // Carregar os dados do arquivo JSON do pet
        String jsonBody = readFileJson("src/test/resources/json/pet1.json");
        int petId = 118650001; // Código esperado do pet

        // Teste pelo Rest-assured
        given()                                          // Dado que
            .contentType(ct)                             // Tipo de conteúdo
            .log().all()                                 // Mostre tudo na ida
            .body(jsonBody)                              // Envie o corpo da requisição

        // Executa
        .when()                                          // Quando
            .post(uriPet)                                // Chamamos o Endpoint fazendo POST

        // Valida 
        .then()                                          // Então
            .log().all()                                 // Mostre tudo na volta
            .statusCode(200)          // Verificar código 200
            .body("name", is("Bob"))                // Verificar se o nome é "Bob"
            .body("id", is(petId))                  // Verificar se o id é "118650001"(Variável petId)
            .body("category.name", is("cachorro"))  // Verificar se o nome da categoria é "cachorro"
            .body("tags[0].name", is("vacinado"))   // Verificar se é "vacinado"
        ; // Fechamento do Given
    }
}