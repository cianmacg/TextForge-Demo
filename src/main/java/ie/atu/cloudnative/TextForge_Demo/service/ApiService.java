package ie.atu.cloudnative.TextForge_Demo.service;

import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class ApiService {
    private final String apiPath = "http://localhost:8081/";
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    public String stem(String text) {
        String request = buildUri(text, "stem/lovins");
        System.out.println("Stemming!");
        return restTemplate.getForObject(request, String.class);
    }

    public String token(String text) {
        try{
            String request = buildUri(text, "token/bpe/");
            System.out.println("Tokenising!");
            return restTemplate.getForObject(request, String.class);
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    private String buildUri(String text, String action) {
        String path = apiPath.concat(action);
        System.out.println("building uri!");

        Map<String, String> body = new HashMap<>();
        body.put("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // 4. Expect String[] back from the API
        ResponseEntity<String[]> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                entity,
                String[].class
        );

        return UriComponentsBuilder.fromUriString(path)
                .build()
                .toUriString();
    }
}
