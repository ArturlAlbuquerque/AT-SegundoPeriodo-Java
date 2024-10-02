package org.example.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.CarroDTOInput;
import org.example.service.CarroService;
import static spark.Spark.*;

public class CarroController {
    private CarroService carroService = new CarroService();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void respostasRequisicoes() {

        get("/carros", (request, response) -> {
            response.type("application/json");
            return objectMapper.writeValueAsString(carroService.listar());
        });

        get("/carros/:id", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            response.type("application/json");
            return objectMapper.writeValueAsString(carroService.buscar(id));
        });

        delete("/carros/:id", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            carroService.excluir(id);
            response.status(204);
            return "";
        });

        post("/carros", (request, response) -> {
            CarroDTOInput carroDTOInput = objectMapper.readValue(request.body(), CarroDTOInput.class);
            carroService.inserir(carroDTOInput);
            response.status(201);
            return "";
        });

        put("/carros", (request, response) -> {
            CarroDTOInput carroDTOInput = objectMapper.readValue(request.body(), CarroDTOInput.class);
            carroService.alterar(carroDTOInput);
            response.status(200);
            return "";
        });
    }
}
