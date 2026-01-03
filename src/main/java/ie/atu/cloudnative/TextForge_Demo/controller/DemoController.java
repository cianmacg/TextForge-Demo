package ie.atu.cloudnative.TextForge_Demo.controller;

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
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new TextRequest("yup", "none"));
        return "index";
    }

    @PostMapping("/process")
    public String process(@ModelAttribute TextRequest request, Model model) {
        String mockResult = "The external API would have " + request.getAction() + "ed: " + request.getContent();
        model.addAttribute("request", new TextRequest("yup", "none"));
        model.addAttribute("result", mockResult);
        return "index";
    }
}
