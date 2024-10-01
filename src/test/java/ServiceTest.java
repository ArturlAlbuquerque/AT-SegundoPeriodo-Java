import org.example.dto.CarroDTOInput;
import org.example.dto.CarroDTOOutput;
import org.example.service.CarroService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceTest {

    @Test
    public void testarInsercaoDeCarro() {
        CarroService carroService = new CarroService();
        CarroDTOInput carro = new CarroDTOInput();
        carro.setModelo("Modelo Teste");
        carro.setPlaca("ABC-1234");
        carro.setChassi("XYZ12345");
        carroService.inserir(carro);
        Collection<CarroDTOOutput> carros = carroService.listar();
        Assertions.assertEquals(1, carros.size());
    }

    @Test
    public void testarListagemDeCarros() throws Exception {
        Thread.sleep(5000);
        URL url = new URL("http://localhost:4567/carros");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        Assertions.assertEquals(200, responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println("Resposta da API: " + response);
    }
}





