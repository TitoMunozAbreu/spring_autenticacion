package com.example.configure;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserDetailsService service;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private HttpServletRequest request;

    @Override
    public Authentication authenticate(Authentication authentication){

        //obtener datos del request
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        //obtner datos de BBDD
        UserDetails user = service.loadUserByUsername(username);
        String password = user.getPassword();

        //comprobar si las contraseña es valida
        if(!isValidPassword(rawPassword,password)){
            throw new BadCredentialsException("Invalid username and password");
        }




        return UsernamePasswordAuthenticationToken.authenticated(
                user,
                user.getPassword(),
                user.getAuthorities());
    }

    private boolean isValidPassword(String rawPassword, String password) {
        return encoder.matches(rawPassword,password);
    }

    //Nos permite definir el tipo de autenticacion
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
