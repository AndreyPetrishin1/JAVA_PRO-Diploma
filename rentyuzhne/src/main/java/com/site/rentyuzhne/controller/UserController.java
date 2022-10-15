package com.site.rentyuzhne.controller;

import com.site.rentyuzhne.model.User;
import com.site.rentyuzhne.service.UserService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Data
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute User user) {
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(@ModelAttribute @Valid User user, BindingResult bildingResult) {
        if(bildingResult.hasErrors()){

            return "registration";
        }
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal,
                          Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }


    @GetMapping("/user/{id}")
    public String UserInfo(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("flats", user.getFlats());
        return "user-info";
    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        return "login";
    }
}