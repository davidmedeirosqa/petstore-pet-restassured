// 1- Nome do pacote

// 2- Bibliotecas
import static io.restassured.RestAssured.given; // Função given

import static org.hamcrest.Matchers.is; // Classe de comparadores do Hamcrest

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.MethodOrderer; // Ordem dos @Test
import org.junit.jupiter.api.Order; // Ordem dos @Test
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ativador da ordem @Test

// 3- Classe
public class TestPet {

    // 3.1- Atributos
    static String ct = "application/json"; // content-type
    static String uriPet = "https://petstore.swagger.io/v2/pet"; // Base URL + Endpoint (user)
    static int petId = 118650001; // Código esperado do pet
    String petName = "Bob";
    String categoryName = "cachorro";
    String tagName = "vacinado";
    String status[] = { "available", "sold" };

    // 3.2 - Funções e métodos
    // 3.2.1 - Funções e métodos comuns/úteis

    // Função de leitura do JSON
    public static String readFileJson(String fileJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileJson)));
    }

    // 3.2.2 - Métodos de testes
    @Test
    @Order(1)

    // #################### POST ####################
    public void testPostPet() throws IOException {

        // Configuração - Entrada e saída
        // Carregar os dados do arquivo JSON do pet1
        String jsonBody = readFileJson("src/test/resources/json/pet1.json");

        // Teste pelo Rest-assured
        given() // Dado que
                .contentType(ct) // Tipo de conteúdo
                .log().all() // Mostre tudo na ida
                .body(jsonBody) // Envie o corpo da requisição

                // Execução
                .when() // Quando
                .post(uriPet) // Chamamos o Endpoint fazendo POST

                // Validação
                .then() // Então
                .log().all() // Mostre tudo na volta
                .statusCode(200) // Verificar código 200
                .body("name", is(petName)) // Verificar se o nome é "Bob"
                .body("id", is(petId)) // Verificar se o id é "118650001"(Variável petId)
                .body("category.name", is(categoryName)) // Verificar se o nome da categoria é "cachorro"
                .body("tags[0].name", is(tagName)) // Verificar se é "vacinado"
        ; // FechamenFo do Given
    }

    @Test
    @Order(2)

    // #################### GET ####################
    public void testGetPet() {

        // Configuração
        // Entrada - petId está definido na classe
        // Saída - Resultados esperados estão definidos na classe

        // Teste pelo Rest-assured
        given()
                .contentType(ct)
                .log().all()
                .header("Authorization", "api_key " + TestUser.testLogin())
                // Get e Del não tem .body()

                // Execução
                .when()
                .get(uriPet + "/" + petId) // Montar o Endpoint da URI + petId

                // Validação
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is(petName))
                .body("id", is(petId))
                .body("category.name", is(categoryName))
                .body("tags[0].name", is(tagName)); // Fechamento do Given
    }

    @Test
    @Order(3)

    // #################### PUT ####################
    public void testPutPet() throws IOException {

        // Configuração
        // Carregar os dados do arquivo JSON do pet2
        String jsonBody = readFileJson("src/test/resources/json/pet2.json");

        given()
                .contentType(ct)
                .log().all()
                .body(jsonBody)

                // Execução
                .when()
                .put(uriPet)

                // Validação
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is(petName))
                .body("id", is(petId))
                .body("category.name", is(categoryName))
                .body("tags[0].name", is(tagName))
                .body("status", is(status[1])) // Mudança de status (available -> sold)
        ; // Fechamento do Given
    }

    @Test
    @Order(4)

    // #################### DEL ####################
    public void testDelPet() {

        // Configuração - Entrada e saída
        given()
                .contentType(ct)
                .log().all()

                // Execução
                .when()
                .delete(uriPet + "/" + petId)

                // Validação
                .then()
                .log().all()
                .statusCode(200) // Comunicação
                .body("code", is(200)) // Apagou
                .body("type", is("unknown")) // Desconhecido
                .body("message", is(String.valueOf(petId))) // Conversão de number para String e verificação da mensagem
        ;
    }
}