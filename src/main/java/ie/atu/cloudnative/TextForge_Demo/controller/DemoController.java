package ie.atu.cloudnative.TextForge_Demo.controller;

import ie.atu.cloudnative.TextForge_Demo.service.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ie.atu.cloudnative.TextForge_Demo.request.TextRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
public class DemoController {
    private final ApiService api = new ApiService();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new TextRequest("yup", "none"));
        return "index";
    }

    @PostMapping("/process")
    public String process(@ModelAttribute TextRequest request, Model model) {
        String response = "";
        System.out.println(request.getAction() + " " + request.getContent());
        response = switch (request.getAction()) {
            case "stem" -> {
                System.out.println("I'm hjereasdtrfaiwehriuafhjeifnkmsbnruyiteguq werthq we!!!ouwweiqhfjan!!");
                yield api.stem(request.getContent());
            }
            case "token" -> api.token(request.getContent());
            default -> response;
        };

        model.addAttribute("response", response);
        model.addAttribute("request", request);
        return "index";
    }
}
