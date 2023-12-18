package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private NotePage notePage;
    private CredentialsPage credentialsPage;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void unauthorizedUserCantAccessHomePage() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void unauthorizedUserCantAccessRandomPage() {
        driver.get("http://localhost:" + this.port + "/random-page");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void verifyThatAfterLogoutUserCantAccessHomePage() {

        signUp("John", "Dell", "johndell", "delljohn");
        login("johndell", "delljohn");

        HomePage homePage = new HomePage(driver);
        homePage.clickLogoutButton();
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void addOrCreateNote() {

        addOrCreateNote("Note title test", "Note description test", Option.NEW);

        notePage.clickOnNotesTab(driver);
        WebDriverWait wait = new WebDriverWait(driver, 2);
        List<String> titles = notePage.getNotesTitles(driver);
        Assertions.assertTrue(titles.contains("Note title test"));

        List<String> descriptions = notePage.getNotesDescriptions(driver);
        Assertions.assertTrue(descriptions.contains("Note description test"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-delete-button")));
        notePage.clickDeleteNoteButton();
    }

    @Test
    public void editNote() {

        addOrCreateNote("Note title test", "Note description test", Option.NEW);

        addOrCreateNote("Edited note title", "Edited description", Option.EDIT);
        WebDriverWait wait = new WebDriverWait(driver, 2);
        notePage.clickOnNotesTab(driver);

        List<String> titles = notePage.getNotesTitles(driver);
        Assertions.assertTrue(titles.contains("Edited note title"));

        List<String> descriptions = notePage.getNotesDescriptions(driver);
        Assertions.assertTrue(descriptions.contains("Edited description"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-delete-button")));
        notePage.clickDeleteNoteButton();
    }

    @Test
    public void deleteNote() {

        addOrCreateNote("Note title test", "Note description test", Option.NEW);
        WebDriverWait wait = new WebDriverWait(driver, 2);
        notePage.clickOnNotesTab(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-delete-button")));
        notePage.clickDeleteNoteButton();

        notePage.clickOnNotesTab(driver);

        List<String> titles = notePage.getNotesTitles(driver);
        Assertions.assertFalse(titles.contains("Edited note title"));

        List<String> descriptions = notePage.getNotesDescriptions(driver);
        Assertions.assertFalse(descriptions.contains("Edited description"));
    }

    @Test
    public void addCredentials() {
        addOrEditCredentials("test.url", "testuser", "testpassword", "", Option.NEW);

        WebDriverWait wait = new WebDriverWait(driver, 2);
        credentialsPage.clickOnCredentialsTab(driver);

        List<String> urls = credentialsPage.getCredentialsUrls(driver);
        Assertions.assertTrue(urls.contains("test.url"));

        List<String> usernames = credentialsPage.getCredentialsUsernames(driver);
        Assertions.assertTrue(usernames.contains("testuser"));

        List<String> passwords = credentialsPage.getCredentialsPasswords(driver);
        Assertions.assertFalse(passwords.contains("testpassword"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-delete-button")));
        credentialsPage.clickDeleteCredentialsButton();
    }

    @Test
    public void editCredentialsAndCheckIfUnencryptedPasswordIsVisible() {
        addOrEditCredentials("test.url", "testuser", "testpassword", "", Option.NEW);

        addOrEditCredentials("editedtest.url", "newtestUser", "newtestpassword", "testpassword", Option.EDIT);

        WebDriverWait wait = new WebDriverWait(driver, 2);
        credentialsPage.clickOnCredentialsTab(driver);

        List<String> urls = credentialsPage.getCredentialsUrls(driver);
        Assertions.assertTrue(urls.contains("editedtest.url"));

        List<String> usernames = credentialsPage.getCredentialsUsernames(driver);
        Assertions.assertTrue(usernames.contains("newtestUser"));

        List<String> passwords = credentialsPage.getCredentialsPasswords(driver);
        Assertions.assertFalse(passwords.contains("newtestpassword"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-delete-button")));
        credentialsPage.clickDeleteCredentialsButton();
    }

    @Test
    public void deleteCredentials() {
        addOrEditCredentials("test.url", "testuser", "testpassword", "", Option.NEW);

        credentialsPage.clickOnCredentialsTab(driver);
        WebDriverWait wait = new WebDriverWait(driver, 2);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-delete-button")));
        credentialsPage.clickDeleteCredentialsButton();

        credentialsPage.clickOnCredentialsTab(driver);

        List<String> urls = credentialsPage.getCredentialsUrls(driver);
        Assertions.assertFalse(urls.contains("editedtest.url"));

        List<String> usernames = credentialsPage.getCredentialsUsernames(driver);
        Assertions.assertFalse(usernames.contains("newtestUser"));
    }


    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    private void signUp(String firstName, String lastName, String username, String password) {

        if (userService.getUser(username) == null) {
            driver.get("http://localhost:" + port + "/signup");
            SignupPage signupPage = new SignupPage(driver);

            WebDriverWait wait = new WebDriverWait(driver, 2);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
            signupPage.enterFirstName(firstName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
            signupPage.enterLastName(lastName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
            signupPage.enterUsername(username);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
            signupPage.enterPassword(password);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
            signupPage.clickSignupButton();

            Assertions.assertFalse(userService.isUsernameAvailable(username));
        }
    }

    private void login(String username, String password) {

        driver.get("http://localhost:" + port + "/login");
        LoginPage loginPage = new LoginPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, 2);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        loginPage.enterUsername(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        loginPage.enterPassword(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        loginPage.clickLoginButton();

        Assertions.assertEquals("Home", driver.getTitle());
    }

    private void addOrCreateNote(String noteTitle, String noteDescription, Option option) {
        WebDriverWait wait = new WebDriverWait(driver, 2);

        if (option == Option.NEW) {
            signUp("John", "Dell", "johndell", "delljohn");
            login("johndell", "delljohn");
            notePage = new NotePage(driver);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
            notePage.clickOnNotesTab(driver);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-button")));
            notePage.clickNewNoteButton();
        } else if (option == Option.EDIT) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
            notePage.clickOnNotesTab(driver);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-edit-button")));
            notePage.clickEditNoteButton();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        notePage.enterNoteTitle(noteTitle);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        notePage.enterNoteDescription(noteDescription);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-button")));
        notePage.clickSaveNoteButton();

        wait.until(ExpectedConditions.titleContains("Home"));
    }

    private void addOrEditCredentials(String url, String username, String password, String oldPassword, Option option) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        credentialsPage = new CredentialsPage(driver);

        if (option == Option.NEW) {
            signUp("John", "Dell", "johndell", "delljohn");
            login("johndell", "delljohn");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
            credentialsPage.clickOnCredentialsTab(driver);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credentials-button")));
            credentialsPage.clickNewCredentialsButton();
        } else if (option == Option.EDIT) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
            credentialsPage.clickOnCredentialsTab(driver);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-edit-button")));
            credentialsPage.clickEditCredentialsButton();
            //check if unencrypted password is visible
            Assertions.assertEquals(oldPassword, credentialsPage.getPassword());
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        credentialsPage.enterUrl(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        credentialsPage.enterUsername(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        credentialsPage.enterPassword(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credentials-button")));
        credentialsPage.clickSaveCredentialsButton();

        wait.until(ExpectedConditions.titleContains("Home"));
    }

    private enum Option {
        NEW,
        EDIT
    }
}
