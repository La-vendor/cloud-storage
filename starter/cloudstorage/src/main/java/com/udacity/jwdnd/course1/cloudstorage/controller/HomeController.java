package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final CredentialsService credentialsService;



    public HomeController(NoteService noteService, UserService userService, CredentialsService credentialsService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialsService = credentialsService;
    }


    @GetMapping("/home")
    public String homeView(@ModelAttribute("newNote") Note note, @ModelAttribute("newCredentials") Credentials credentials, Authentication authentication, Model model) {

        Integer activeUserId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("noteList", this.noteService.getNotes(activeUserId));
        model.addAttribute("credentialsList", this.credentialsService.getCredentials(activeUserId));
        return "home";
    }


    @PostMapping("/add-note")
    public String addNote(@ModelAttribute("newNote") Note note, Authentication authentication, Model model) {

        Integer activeUserId = userService.getUser(authentication.getName()).getUserId();
        note.setUserId(activeUserId);
        this.noteService.addNote(note);
        model.addAttribute("noteList", this.noteService.getNotes(activeUserId));
        return "home";
    }

    @PostMapping("/add-credentials")
    public String addCredentials(@ModelAttribute("newCredentials") Credentials credentials, Authentication authentication, Model model){

        Integer activeUserId = userService.getUser(authentication.getName()).getUserId();
        credentials.setUserId(activeUserId);
        this.credentialsService.addCredentials(credentials);
        model.addAttribute("credentialsList", this.credentialsService.getCredentials(activeUserId));

        return "home";
    }

}
