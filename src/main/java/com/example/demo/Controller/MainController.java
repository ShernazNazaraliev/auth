package com.example.demo.Controller;
import com.example.demo.DTO.AuthenticationResponse;
import com.example.demo.Service.UserService;
import com.example.demo.jwt.JwtUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/")
    public String helloWorld() {
        return "you don't need to be logged in";
    }

    @GetMapping("/not-restricted")
    public String notRestricted() {
        return "you don't need to be logged in";
    }



    @RequestMapping("/auth-with-google")
    public AuthenticationResponse restricted(Principal principal) throws ChangeSetPersister.NotFoundException {

        if (principal == null){
            return new AuthenticationResponse("Вы не зарегистрированы!!!");
        }

        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = defaultOidcUser.getEmail();



        if (userService.loadUserByUsername(email)==null){
            return new AuthenticationResponse("Вы не зарегистрированы!!!");
        }
        else{
            return jwtUtils.generateToken(userService.loadUserByUsername(email));
        }
    }
}
