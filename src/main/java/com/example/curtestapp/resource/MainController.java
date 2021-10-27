package com.example.curtestapp.resource;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
/*@RequestMapping*/
public class MainController {


    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("eventName", "CURRENCY PAGE");
        return "index";
    }

}
