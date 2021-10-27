package com.example.curtestapp.appuser.registration;

import com.example.curtestapp.appuser.AppUser;
import com.example.curtestapp.appuser.AppUserRole;
import com.example.curtestapp.appuser.AppUserService;
import com.example.curtestapp.appuser.currencies.Currency;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;




@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping
    public String register(@RequestBody RegistrationRequest request){


        return registrationService.register(request);

    }




/*    AppUserService appUserService;

    private RegistrationService registrationService;


    @GetMapping()
    public String registerPage(Model model){


        return "registration";

    }


    @PostMapping("/register")
    public String registrationComplete(Model model, @RequestParam String username,
    @RequestParam String password) {


        appUserService.singUpUser(new AppUser("Keni","password",AppUserRole.USER));

        return "registration";
    }*/

}
