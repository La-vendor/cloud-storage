package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
//    @FindBy(id = "success-msg")
//    private WebElement successMessage;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstNameField;

    @FindBy(id = "inputLastName")
    private WebElement inputLastNameField;

    @FindBy(id = "inputUsername")
    private WebElement inputUsernameField;

    @FindBy(id = "inputPassword")
    private WebElement inputPasswordField;

    @FindBy(id = "buttonSignUp")
    private WebElement signupButton;

    public SignupPage(WebDriver webDriver){
        PageFactory.initElements(webDriver,this);
    }
//
//    public String  getSuccessMessage(){
//        return successMessage.getText();
//    }
    public void enterFirstName(String firstName){
        inputFirstNameField.sendKeys(firstName);
    }
    public void enterLastName(String lastName){
        inputLastNameField.sendKeys(lastName);
    }

    public void enterUsername(String username){
        inputUsernameField.sendKeys(username);
    }
    public void enterPassword(String password){
        inputPasswordField.sendKeys(password);
    }
    public void clickSignupButton(){
        signupButton.click();
    }
}
