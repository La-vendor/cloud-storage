package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private String warningMessage;

    public MessageService() {
    }

    public void setWarningMessage(String warningMessage){
        this.warningMessage = warningMessage;
    }

    public void clearWarningMessage(){
        this.warningMessage = null;
    }

    public String getWarningMessage(){
        return this.warningMessage;
    }
}
