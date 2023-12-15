package com.udacity.jwdnd.course1.cloudstorage.exception;

import com.udacity.jwdnd.course1.cloudstorage.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

     @Autowired
    MessageService messageService;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model, MaxUploadSizeExceededException e, RedirectAttributes attributes) {

//        RedirectView redirectView = new RedirectView("/home", true);
//        attributes.addFlashAttribute("fileWarningMessage", "File is too large!");
        messageService.setWarningMessage("File is too large!");
        return "redirect:/home";
    }
}
