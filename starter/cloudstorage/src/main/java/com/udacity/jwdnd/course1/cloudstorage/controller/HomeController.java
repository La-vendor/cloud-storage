package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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


    public Integer getActiveUserId(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUser(authentication.getName());
            if (user != null) return user.getUserId();
        }
        return null;
    }

    @GetMapping("/home")
    public String homeView(@ModelAttribute("userNote") Note note, @ModelAttribute("newCredentials") Credentials credentials, Authentication authentication, Model model) {

        Integer activeUserId = getActiveUserId(authentication);
        if (activeUserId != null) {
            model.addAttribute("noteList", this.noteService.getNotes(activeUserId));
            model.addAttribute("credentialsList", this.credentialsService.getCredentials(activeUserId));
        }
        return "home";
    }


    @PostMapping("/note")
    public String addOrUpdateNote(@ModelAttribute("userNote") Note note, Authentication authentication) {

        if (note.getNoteId() == null) {
            Integer activeUserId = getActiveUserId(authentication);
            if (activeUserId != null) {
                note.setUserId(activeUserId);
                this.noteService.addNote(note);
            }
        } else {
            this.noteService.updateNote(note);
        }
        return "redirect:/home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") String stringNoteId) {
        Integer noteId = Integer.valueOf(stringNoteId);
        this.noteService.deleteNote(noteId);
        return "redirect:/home";
    }

    @PostMapping("/credentials")
    public String addOrUpdateCredentials(@ModelAttribute("newCredentials") Credentials credentials, Authentication authentication, Model model) {

        if (credentials.getCredentialId() == null) {
            Integer activeUserId = getActiveUserId(authentication);
            if (activeUserId != null) {
                credentials.setUserId(activeUserId);
                this.credentialsService.addCredentials(credentials);
            }
        }
        else{
            this.credentialsService.updateCredentials(credentials);
        }
        return "redirect:/home";
    }

    @GetMapping("/credentials/delete/{credentialId}")
    public String deleteCredentials(@PathVariable("credentialId") String stringCredentialId) {

        Integer credentialId = Integer.valueOf(stringCredentialId);
        this.credentialsService.deleteCredentials(credentialId);
        return "redirect:/home";
    }

}
