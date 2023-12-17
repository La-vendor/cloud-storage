package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CredentialsPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id="new-credentials-button")
    private WebElement addNewCredentialsButton;

    @FindBy(id="credential-url")
    private  WebElement credentialsUrlTextField;

    @FindBy(id="credential-username")
    private  WebElement credentialsUsernameTextField;

    @FindBy(id="credential-password")
    private  WebElement credentialsPasswordTextField;

    @FindBy(id = "save-credentials-button")
    private WebElement saveCredentialsButton;

    @FindBy(id="credentials-edit-button")
    private WebElement editCredentialsButton;

    @FindBy(id="credentials-delete-button")
    private WebElement deleteCredentialsButton;

    @FindBy(id="credentialTable")
    private WebElement credentialTable;

    public CredentialsPage(WebDriver webDriver){
        PageFactory.initElements(webDriver,this);
    }

    public void clickOnCredentialsTab(WebDriver driver){
        credentialsTab.click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
    }

    public void clickNewCredentialsButton(){
        addNewCredentialsButton.click();
    }

    public void enterUrl(String url){
        credentialsUrlTextField.clear();
        credentialsUrlTextField.sendKeys(url);
    }

    public void enterUsername(String username){
        credentialsUsernameTextField.clear();
        credentialsUsernameTextField.sendKeys(username);
    }

    public void enterPassword(String password){
        credentialsPasswordTextField.clear();
        credentialsPasswordTextField.sendKeys(password);
    }

    public void clickSaveCredentialsButton(){
        saveCredentialsButton.click();
    }

    public void clickEditCredentialsButton(){
        editCredentialsButton.click();
    }

    public void clickDeleteCredentialsButton(){
        deleteCredentialsButton.click();
    }

    public String getPassword(){
        return credentialsPasswordTextField.getAttribute("value");
    }

    public List<String> getCredentialsUrls(){
        List<WebElement> credentials = credentialTable.findElements(By.id("credentials-url"));
        List<String> extractedStrings = new ArrayList<>();
        for(WebElement credential : credentials){
            extractedStrings.add(credential.getText());
        }
        return extractedStrings;
    }

    public List<String> getCredentialsUsernames(){
        List<WebElement> credentials = credentialTable.findElements(By.id("credentials-username"));
        List<String> extractedStrings = new ArrayList<>();
        for(WebElement credential : credentials){
            extractedStrings.add(credential.getText());
        }
        return extractedStrings;
    }

    public List<String> getCredentialsPasswords(){
        List<WebElement> credentials = credentialTable.findElements(By.id("credentials-password"));
        List<String> extractedStrings = new ArrayList<>();
        for(WebElement credential : credentials){
            extractedStrings.add(credential.getText());
        }
        return extractedStrings;
    }

}

