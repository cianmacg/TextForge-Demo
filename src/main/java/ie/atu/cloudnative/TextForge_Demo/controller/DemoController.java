package ie.atu.cloudnative.TextForge_Demo.controller;

import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ie.atu.cloudnative.TextForge_Demo.request.TextRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DemoController {
    private final String apiPath = "http://api-service:8081/";
    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new TextRequest("Sample text for stemming, encoding, or decoding...", "none"));
        return "index";
    }

    @PostMapping("/process")
    public String process(@ModelAttribute TextRequest request, Model model) {

        System.out.println(request.getAction() + " " + request.getContent());
        switch (request.getAction()) {
            case "stem" -> stem(request.getContent(), model);
            case "encode" -> encode(request.getContent(), model);
            case "decode" -> decode(request.getContent(), model);
            default -> System.out.println("Invalid action requested: " + request.getAction());
        };

        model.addAttribute("request", request);
        return "index";
    }

    private void stem(String text, Model model) {
        String path = apiPath.concat("stem/lovins");
        HttpEntity<Map<String, String>> entity = buildUri(text);

        try {
            ResponseEntity<String[]> response = restTemplate.exchange(
                    path,
                    HttpMethod.GET,
                    entity,
                    String[].class
            );

            String combinedResult = String.join(" ", response.getBody());
            model.addAttribute("result", combinedResult);

        } catch (Exception e) {
            model.addAttribute("result", "API Error: " + e.getMessage());
        }
    }

    private void encode(String text, Model model) {
        String path = apiPath.concat("token/encode");
        HttpEntity<Map<String, String>> entity = buildUri(text);

        try {
            ResponseEntity<String[]> response = restTemplate.exchange(
                    path,
                    HttpMethod.GET,
                    entity,
                    String[].class
            );
            System.out.println(Arrays.stream(response.getBody()).toArray());
            String combinedResult = String.join(" ", response.getBody());
            model.addAttribute("result", combinedResult);

        } catch (Exception e) {
            model.addAttribute("result", "API Error: " + e.getMessage());
        }
    }

    private void decode(String text, Model model) {
        String path = apiPath.concat("token/decode");

        int[] tokens = Arrays.stream(text.split(" "))
                .map(String::trim)               // Clean up whitespace
                .filter(s -> s.matches("\\d+"))  // Only allow digits
                .mapToInt(Integer::parseInt)     // Convert to int
                .toArray();

        System.out.println("1");
        Map<String, int[]> body = new HashMap<>();
        body.put("tokens", tokens);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, int[]>> entity = new HttpEntity<>(body, headers);
        System.out.println("2");
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    path,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            System.out.println(response.getBody().toString());
            model.addAttribute("result", response.getBody());

        } catch (Exception e) {
            model.addAttribute("result", "API Error: " + e.getMessage());
        }
    }

    private HttpEntity<Map<String, String>> buildUri(String text) {
        Map<String, String> body = new HashMap<>();
        body.put("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(body, headers);
    }
}


