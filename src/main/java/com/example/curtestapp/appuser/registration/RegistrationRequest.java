package com.example.curtestapp.appuser.registration;


import com.example.curtestapp.appuser.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String username;
    private final String password;

}
