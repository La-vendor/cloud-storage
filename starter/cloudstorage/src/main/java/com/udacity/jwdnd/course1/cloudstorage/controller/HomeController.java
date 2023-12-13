package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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



    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @GetMapping("/home")
    public String homeView(@ModelAttribute("newNote") Note note, Authentication authentication, Model model) {

        Integer activeUserId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("noteList", this.noteService.getNotes(activeUserId));
        return "home";
    }


    @PostMapping("/add-note")
    public String addNote(@ModelAttribute("newNote") Note note, Authentication authentication, Model model) {

        Integer activeUserId = userService.getUser(authentication.getName()).getUserId();
        note.setUserId(activeUserId);
        noteService.addNote(note);
        model.addAttribute("noteList", this.noteService.getNotes(activeUserId));
        return "home";
    }

    @PostMapping("/add-credentials")
    public String addCredentials(@ModelAttribute("newCredentials")Credentials credentials, Authentication authentication, Model model){

        Integer activeUserId = userService.getUser(authentication.getName()).getUserId();
        credentials.setUserId(activeUserId);

        return "home";
    }

}
