package hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("goodbye")
    public String goodbye(@RequestParam String name, Model model) {
        String goodbye = "Goodbye, " + name + "!";
        model.addAttribute("greeting", goodbye);

        return "hello";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "hello")
    @ResponseBody
    public String helloWithQueryParam(@RequestParam String name, Model model) {
        String greeting = "Hello, " + name + "!";
        model.addAttribute("greeting", greeting);

        return "hello";
    }

    @GetMapping("hello/{name}")
    public String helloWithPathParam(@PathVariable String name, Model model) {
        String greeting = "Hello, " + name + "!";
        model.addAttribute("greeting", greeting);

        return "hello";
    }

    @GetMapping("form")
    public String HelloForm() {
        return "form";
    }

    @GetMapping("hello-names")
    public String helloNames(Model model){
        List<String> names = new ArrayList<>();
        names.add("Darius");
        names.add("Marius");
        names.add("Radu");
        model.addAttribute("names", names);

        return "hello-list";
    }
}
