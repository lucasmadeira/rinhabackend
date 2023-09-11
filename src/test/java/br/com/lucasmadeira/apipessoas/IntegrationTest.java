package br.com.lucasmadeira.apipessoas;

import br.com.lucasmadeira.apipessoas.usecase.dto.OutputCadastrarPessoaDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testApiIntegrationWithTSVFile() throws IOException {
        String tsvFilePath = "payloads/pessoas-payloads.tsv";
        File file = new File(tsvFilePath);
        InputStream resourceAsStream = new FileInputStream(file);



        Map<String, Integer> mapStatusCode = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Dividir a linha TSV para obter os campos
                String[] fields = line.split("\t");

                // Construir o payload ou URL da requisição
                String payload = fields[0];  // Ou construa conforme necessário

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);


                ResponseEntity<String> response = restTemplate.exchange(
                        "/pessoas",
                        HttpMethod.POST,
                        new HttpEntity<>(payload, headers),
                        String.class
                );

                if(mapStatusCode.containsKey(response.getStatusCode().toString())){
                    mapStatusCode.put(response.getStatusCode().toString(), mapStatusCode.get(response.getStatusCode().toString()) + 1);
                    continue;
                }
                mapStatusCode.put(response.getStatusCode().toString(), 1);

            }

            System.out.println(mapStatusCode);
        }
    }
}
