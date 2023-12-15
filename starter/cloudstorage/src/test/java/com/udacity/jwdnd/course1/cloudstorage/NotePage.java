package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    @FindBy(id = "userTable")
    private WebElement notesTable;

    @FindBy(id = "note-edit-button")
    private WebElement editNoteButton;



    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void clickOnNotesTab() {
        notesTab.click();
    }

    public void clickNewNoteButton() {
        addNewNoteButton.click();
    }

    public void enterNoteTitle(String noteTitle) {
        noteTitleTextField.sendKeys(noteTitle);
    }

    public void enterNoteDescription(String noteDescription) {
        noteDescriptionTextField.sendKeys(noteDescription);
    }

    public void clickSaveNoteButton() {
        saveNoteButton.click();
    }

    public void clickEditNoteButton(){
        editNoteButton.click();
    }

    public List<String> getNotesTitles() {
        List<WebElement> notes = notesTable.findElements(By.tagName("tr"));
        List<String> titles = new ArrayList<>();
        for (WebElement note : notes) {

            titles.add(note.findElement(By.tagName("th")).getText());
        }
        return titles;
    }

    public List<String> getNotesDescriptions() {
        List<WebElement> notes = notesTable.findElements(By.id("note-descriptions"));
        List<String> descriptions = new ArrayList<>();
        for (WebElement note : notes) {
            descriptions.add(note.getText());
        }
        return descriptions;
    }

}
