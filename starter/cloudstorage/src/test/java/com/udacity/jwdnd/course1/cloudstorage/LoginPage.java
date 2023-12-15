package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsernameField;

    @FindBy(id = "inputPassword")
    private WebElement inputPasswordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public LoginPage(WebDriver webDriver){
        PageFactory.initElements(webDriver,this);
    }

    public void enterUsername(String username){
        inputUsernameField.sendKeys(username);
    }
    public void enterPassword(String password){
        inputPasswordField.sendKeys(password);
    }
    public void clickLoginButton(){
        loginButton.click();
    }


}
