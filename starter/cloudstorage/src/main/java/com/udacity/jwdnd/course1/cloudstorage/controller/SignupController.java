package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    /*
    This is a solution proposed in https://knowledge.udacity.com/questions/762223
    When this function was originally returning String "login",
     page login was correctly loaded but the URL remain unchanged.
     Solution proposed by Mustafa A. enabled to pass all test prepared by Udacity.
     */
    @PostMapping()
    public RedirectView signupUser(@ModelAttribute User user, RedirectAttributes attributes) {
        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            RedirectView redirectView = new RedirectView("/login", true);
            attributes.addFlashAttribute("signupSuccess", true);
            return redirectView;
        }

        RedirectView redirectView = new RedirectView("/signup", true);
        attributes.addFlashAttribute("signupError", signupError);
        return redirectView;
    }
}