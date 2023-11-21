package com.example.configure;

import com.example.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {



    //SecurityFilterChain es donde se especifica el uso del spring security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //desabilitar el csrf
        http.csrf(csrf -> csrf.disable());

        //personalizar urls que requieran o no, autenticación
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/", "/images/**", "/auth/**").permitAll()
                    .requestMatchers("/users").hasAnyRole("USER","ADMIN")
                    .anyRequest().authenticated();})

            //permite realizar pruebas APIRest con autenticación basica
            .httpBasic()
            .and()

            //configuracion de la pagina de login
            .formLogin(loginConfigurer -> {
                loginConfigurer.loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/users")
                .permitAll();})

            //configuracion de la pagina de logut
            .logout(logoutConfigurer -> {
                logoutConfigurer.logoutUrl("/logout")
                        .permitAll();});

        //se personaliza un provider con ciertas validaciones
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login"); // Redirigir a /login en caso de fallo
    }


    //Nos creamos este Bean para dar uso al AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthenticationProviderImpl();
    }
}
