package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final CredentialsService credentialsService;
    private final FileService fileService;
    private final MessageService messageService;
    private final EncryptionService encryptionService;


    public HomeController(NoteService noteService, UserService userService, CredentialsService credentialsService, FileService fileService, MessageService messageService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialsService = credentialsService;
        this.fileService = fileService;
        this.messageService = messageService;
        this.encryptionService = encryptionService;
    }


    public Integer getActiveUserId(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUser(authentication.getName());
            if (user != null) return user.getUserId();
        }
        return null;
    }

    @GetMapping("/home")
    public String homeView(@ModelAttribute("userNote") Note note,
                           @ModelAttribute("newCredentials") Credentials credentials,
                           Authentication authentication,
                           Model model) {
        model.addAttribute("resultMessage", messageService.getResultMessage());
        model.addAttribute("fileWarningMessage", messageService.getWarningMessage());
        Integer activeUserId = getActiveUserId(authentication);
        if (activeUserId != null) {
            model.addAttribute("encryptionService", encryptionService);
            model.addAttribute("filesList", this.fileService.getFiles(activeUserId));
            model.addAttribute("noteList", this.noteService.getNotes(activeUserId));
            model.addAttribute("credentialsList", this.credentialsService.getCredentials(activeUserId));
        }
        return "home";
    }

    @PostMapping("/clear-message")
    public String clearMessage(){
        messageService.clearResultMessage();
        return "redirect:/home";
    }

    @PostMapping("/file/upload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile, Authentication authentication, Model model) {

        if (fileService.getFileByName(getActiveUserId(authentication), multipartFile.getOriginalFilename()) != null) {
            messageService.setWarningMessage("This file name already exists. Please rename or choose another file.");
        } else {
            messageService.clearWarningMessage();
            fileService.addFile(multipartFile, getActiveUserId(authentication));
            messageService.setResultMessage("File was successfully added.");
        }
        return "redirect:/home";
    }

    @GetMapping("/file/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String stringFileId) {

        Integer fileId = Integer.valueOf(stringFileId);
        File file = fileService.getFileById(fileId);

        if (file != null) {
            ByteArrayResource resource = new ByteArrayResource(file.getFileData());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .contentLength(file.getFileData().length)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/file/delete/{fileId}")
    public String fileDelete(@PathVariable("fileId") String stringFileId) {
        Integer fileId = Integer.valueOf(stringFileId);
        this.fileService.deleteFile(fileId);
        messageService.setResultMessage("File was successfully deleted.");
        return "redirect:/home";
    }


    @PostMapping("/note")
    public String addOrUpdateNote(@ModelAttribute("userNote") Note note, Authentication authentication) {

        if (note.getNoteId() == null) {
            Integer activeUserId = getActiveUserId(authentication);
            if (activeUserId != null) {
                note.setUserId(activeUserId);
                this.noteService.addNote(note);
                messageService.setResultMessage("Note was successfully added.");
            }
        } else {
            this.noteService.updateNote(note);
            messageService.setResultMessage("Note was successfully updated.");
        }
        return "redirect:/home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") String stringNoteId) {
        Integer noteId = Integer.valueOf(stringNoteId);
        this.noteService.deleteNote(noteId);
        messageService.setResultMessage("Note was successfully deleted.");
        return "redirect:/home";
    }

    @PostMapping("/credentials")
    public String addOrUpdateCredentials(@ModelAttribute("newCredentials") Credentials credentials, Authentication authentication, Model model) {

        if (credentials.getCredentialId() == null) {
            Integer activeUserId = getActiveUserId(authentication);
            if (activeUserId != null) {
                credentials.setUserId(activeUserId);
                this.credentialsService.addCredentials(credentials);
                messageService.setResultMessage("Credentials were successfully added.");
            }
        } else {

            this.credentialsService.updateCredentials(credentials);
            messageService.setResultMessage("Credentials were successfully changed.");
        }
        return "redirect:/home";
    }

    @GetMapping("/credentials/delete/{credentialId}")
    public String deleteCredentials(@PathVariable("credentialId") String stringCredentialId) {

        Integer credentialId = Integer.valueOf(stringCredentialId);
        this.credentialsService.deleteCredentials(credentialId);
        messageService.setResultMessage("Credentials were successfully deleted.");
        return "redirect:/home";
    }

}
