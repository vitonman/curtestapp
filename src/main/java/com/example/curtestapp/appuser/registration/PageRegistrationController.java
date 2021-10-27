package com.example.curtestapp.appuser.registration;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageRegistrationController {


    private final RegistrationController registrationController;

    @Autowired
    public PageRegistrationController(RegistrationController registrationController) {
        this.registrationController = registrationController;
    }


    @GetMapping("/registration")
    public String registerPage(Model model){


        return "registration";

    }


    @PostMapping("/register")
    public String registrationComplete(Model model,
                                       @RequestParam String userName,
                                       @RequestParam String userPassword) {


        registrationController.register(new RegistrationRequest(userName,userPassword));
        System.out.println(userName + "         loginpass         " + userPassword);

        return "redirect:/login";
    }

}
