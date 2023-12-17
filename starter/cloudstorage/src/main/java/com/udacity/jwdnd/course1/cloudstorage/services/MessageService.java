package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private String warningMessage;
    private String  resultMessage;

    public MessageService() {
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public void setWarningMessage(String warningMessage){
        this.warningMessage = warningMessage;
    }

    public void clearWarningMessage(){
        this.warningMessage = null;
    }

    public void clearResultMessage(){
        this.resultMessage = null;
    }

    public String getWarningMessage(){
        return this.warningMessage;
    }

    public Object getResultMessage() {
        return this.resultMessage;
    }
}
