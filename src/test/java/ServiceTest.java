import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.CarroDTOInput;
import org.example.dto.CarroDTOOutput;
import org.example.service.CarroService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


// Execute classe main antes dos testes
public class ServiceTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testarInsercaoDeCarro() {
        CarroService carroService = new CarroService();
        CarroDTOInput carro = new CarroDTOInput();
        carro.setModelo("Modelo Teste");
        carro.setPlaca("ABC-1234");
        carro.setChassi("XYZ12345");
        carroService.inserir(carro);
        Collection<CarroDTOOutput> carros = carroService.listar();
        assertEquals(1, carros.size());
    }

    @Test
    public void testarListagemDeCarros() throws Exception {
        Thread.sleep(5000);
        URL url = new URL("http://localhost:4567/carros");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        assertEquals(200, responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println("Resposta da API: " + response);
    }

    @Test
    public void testInserirCarro() throws IOException {
        URL urlPublica = new URL("https://freetestapi.com/api/v1/cars");
        HttpURLConnection connectionPublica = (HttpURLConnection) urlPublica.openConnection();
        connectionPublica.setRequestMethod("GET");
        if (connectionPublica.getResponseCode() != 200) {
            fail("Falha na requisição à API pública: " + connectionPublica.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connectionPublica.getInputStream())));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        } br.close();

        JSONArray carroJsonArr = new JSONArray(response.toString());

        JSONObject carroPublico = carroJsonArr.getJSONObject(0);
        String modelo = carroPublico.getString("model");
        String placa = "ABC123";
        String chassi = gerarNumeroChassi();

        JSONObject novoCarro = new JSONObject();
        novoCarro.put("modelo", modelo);
        novoCarro.put("placa", placa);
        novoCarro.put("chassi", chassi);
        System.out.println(novoCarro);

        URL urlMinhaApi = new URL("http://localhost:4567/carros");
        HttpURLConnection connectionMinhaApi = (HttpURLConnection) urlMinhaApi.openConnection();
        connectionMinhaApi.setDoOutput(true);
        connectionMinhaApi.setRequestProperty("Content-Type", "application/json");
        connectionMinhaApi.setRequestMethod("POST");

        OutputStream os = connectionMinhaApi.getOutputStream();
        os.write(novoCarro.toString().getBytes());
        os.flush();

        int responseCode = connectionMinhaApi.getResponseCode();
        assertEquals(201, responseCode, "Falha na inserção do carro: Código de retorno diferente de 201.");
        connectionMinhaApi.disconnect();
    }

   private String gerarNumeroChassi() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder chassi = new StringBuilder();
        for (int i = 0; i < 17; i++) {
            chassi.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return chassi.toString();
   }
}





