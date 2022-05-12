package petsore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade pet - add um novo pet

    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Criando um novo pet - POST
    @Test // esta notação identifica p/ o TesteNG o método ou função como um teste
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json"); // só muda o caminho e nome do arquivo json
        // configurando o Rest-assured no estilo Gherkin
        given()
                .contentType("application/json") // comun em API REST | antigas seria o ("text/xml")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Charlotte"))
                .body("category.name", is("dog")) // is é usado para quando vc tem a informação simples entre as {}
                .body("tags.name", contains("cute")) // usado para quando o que vc quer esta num array dentro do json []
        ;
    }
    @Test
    public void consultarPet(){
        String petId = "2026252730";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri+"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Charlotte"))
                .body("category.name", is("dog"))
                .body("status", is("available"))
        ;
    }
}
