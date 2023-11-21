package com.example.controller;

import com.example.exceptions.UserExistException;
import com.example.models.Role;
import com.example.models.UserEntity;
import com.example.models.UserLoginRequest;
import com.example.models.UserRequest;
import com.example.repository.UserEntityRepository;
import com.example.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserEntityRepository repository;
    @Autowired
    private UserDetailsServiceImpl service;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Home");
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("userLogin",new UserLoginRequest());
        return "login";
    }

    @GetMapping("/auth/register")
    public String registrarUsuario(Model model){
        model.addAttribute("userRequest", new UserRequest());
        model.addAttribute("roles", Role.values());
        return "register";
    }

    @PostMapping("/auth/register")
    public String autenticar(@ModelAttribute("userRequest")
                             @Valid UserRequest userRequest,
                             BindingResult result,
                             Model model,
                             RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute("roles", Role.values());
            return "register";
        }
        model.addAttribute("title", "Home-User");
        //comprobar si existe usuario
        boolean userExist = this.repository.findByEmail(userRequest.getEmail())
                .isPresent();

        if(userExist){
            attributes.addFlashAttribute("danger", " Email already exists");
            return "redirect:/auth/register";
        }

        this.service.saveUser(userRequest);
        attributes.addFlashAttribute("success", " User successfully registered");
        return "redirect:/login";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users")
    public String autenticado(Model model, HttpSession session){
        UserEntity userSession = (UserEntity) session.getAttribute("userSession");
        model.addAttribute("users", this.repository.findAll());
        model.addAttribute("isLogin",true);
        model.addAttribute("userName", userSession.getFirstName());

        return "users-config";
    }
}
