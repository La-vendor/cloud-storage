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

public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "add-note-button")
    private WebElement addNewNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleTextField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionTextField;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "notesTable")
    private WebElement notesTable;

    @FindBy(id = "note-edit-button")
    private WebElement editNoteButton;

    @FindBy(id = "note-delete-button")
    private WebElement deleteNoteButton;

    @FindBy(id ="note-titles")
    private WebElement noteTitles;



    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void clickOnNotesTab(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        notesTab.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notesTable")));
    }

    public void clickNewNoteButton() {
        addNewNoteButton.click();
    }

    public void enterNoteTitle(String noteTitle) {
        noteTitleTextField.clear();
        noteTitleTextField.sendKeys(noteTitle);
    }

    public void enterNoteDescription(String noteDescription) {
        noteDescriptionTextField.clear();
        noteDescriptionTextField.sendKeys(noteDescription);
    }

    public void clickSaveNoteButton() {
        saveNoteButton.click();
    }

    public void clickEditNoteButton(){
        editNoteButton.click();
    }

    public void clickDeleteNoteButton(){
        deleteNoteButton.click();
    }

    public List<String> getNotesTitles(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notesTable")));
        List<WebElement> notes = notesTable.findElements(By.id("note-titles"));
        List<String> titles = new ArrayList<>();
        for (WebElement note : notes) {
            titles.add(note.getText());
        }
        return titles;
    }

    public List<String> getNotesDescriptions(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notesTable")));
        List<WebElement> notes = notesTable.findElements(By.id("note-descriptions"));
        List<String> descriptions = new ArrayList<>();
        for (WebElement note : notes) {
            descriptions.add(note.getText());
        }
        return descriptions;
    }

}
