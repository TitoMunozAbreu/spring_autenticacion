package com.example.service;

import com.example.models.UserEntity;
import com.example.models.UserRequest;
import com.example.repository.UserEntityRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserEntityRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email){
        System.out.println("loadUserByUsername");


        Optional<UserEntity> user = this.repository.findByEmail(email);

        if(user.isPresent()){
            //alamcenar la sesion del usuario
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attributes.getRequest().getSession();
            session.setAttribute("userSession", user.get());
        }

        return user.orElseThrow(() -> new UsernameNotFoundException("User mail not found"));
    }

    @Transactional
    public void saveUser(UserRequest userRequest) {
        //registar usario
        UserEntity user = userRequest.toUserEnity();
        //codificar contraseña
        user.setPassword(encoder.encode(user.getPassword()));

        this.repository.save(user);
    }
}
