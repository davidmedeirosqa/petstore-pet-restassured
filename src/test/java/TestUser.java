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
    static int petId = 118650001; // Código esperado do pet

    // 3.2 - Funções e métodos
    // 3.2.1 - Funções e métodos comuns/úteis

    // Função de leitura do JSON
    public static String readFileJson(String fileJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileJson)));
    }

    // 3.2.2 - Métodos de testes
    @Test

    // #################### POST ####################
    public void testPostPet() throws IOException {

        // Configuração        
        // Carregar os dados do arquivo JSON do pet
        String jsonBody = readFileJson("src/test/resources/json/pet1.json");

        // Teste pelo Rest-assured
        given()                                          // Dado que
            .contentType(ct)                             // Tipo de conteúdo
            .log().all()                                 // Mostre tudo na ida
            .body(jsonBody)                              // Envie o corpo da requisição

        // Executa
        .when()                                          // Quando
            .post(uriPet)                                // Chamamos o Endpoint fazendo POST

        // Valida 
        .then()                                                // Então
            .log().all()                                       // Mostre tudo na volta
            .statusCode(200)                // Verificar código 200
            .body("name", is("Bob"))                // Verificar se o nome é "Bob"
            .body("id", is(petId))                        // Verificar se o id é "118650001"(Variável petId)
            .body("category.name", is("cachorro"))  // Verificar se o nome da categoria é "cachorro"
            .body("tags[0].name", is("vacinado"))   // Verificar se é "vacinado"
        ; // Fechamento do Given
    }

    @Test

    // #################### GET ####################
    public void testGetPet(){

        // Configuração        
        // Entrada - petId está definido na classe -> Linha 21
        // Saída - Resultados esperados
        String petName = "Bob";
        String categoryName = "cachorro";
        String tagName = "vacinado";

        // Teste pelo Rest-assured
        given()
            .contentType(ct)
            .log().all()
            // Get e Del não tem .body()
        
        // Executa
        .when()
            .get(uriPet + "/" + petId) // Montar o Endpoint da URI + petId
        
        // Valida
        .then()
            .log().all()
            .statusCode(200) 
            .body("name", is(petName))                
            .body("id", is(petId))       
            .body("category.name", is(categoryName))                
            .body("tags[0].name", is(tagName))   

        ; // Fechamento do Given
    }
}