package com.example.curtestapp.appuser.registration;

import com.example.curtestapp.appuser.AppUser;
import com.example.curtestapp.appuser.AppUserRole;
import com.example.curtestapp.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private UsernameValidator usernameValidator;

    public String register(RegistrationRequest request){

        boolean isValidUsername = usernameValidator.test(request.getUsername());

        if(!isValidUsername){
            throw new IllegalStateException("username not valid");
        }
        return appUserService.singUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
    //valid or not logic

}
