package com.manreet.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Feature;
import org.testng.annotations.Listeners;
import io.qameta.allure.testng.AllureTestNg;
import java.util.List;
import java.time.Duration;

@Listeners(AllureTestNg.class)  // Listener for generating Allure reports
public class LoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:/Users/hargu/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");

        // Initialize the ChromeDriver
        driver = new ChromeDriver();  // Use the class-level driver here

        // Navigate to the login page and maximize the window
        driver.get("https://app.germanyiscalling.com/common/login/");
        driver.manage().window().maximize();
    }

    @Test
    @Feature("Login Feature")  // Define the feature being tested
    @Description("Test Description: Verifying a successful login using valid credentials.")
    public void successfulLogin() {
        // Perform login and verify successful login
        login("manreetkaur3018@gmail.com", "Test@1234");
        verifySuccessfulLogin();
    }

    @Test
    @Feature("Login Feature")
    @Description("Test Description: Verifying an unsuccessful login using invalid credentials.")
    public void unsuccessfulLogin() {
        // Perform login and verify unsuccessful login
        login("manreetkaur3018@gmail.com", "123456");
        verifyUnsuccessfulLogin();
    }

    @Test
    @Feature("Login Feature")
    @Description("Test Description: Verifying the login page's response to empty email and password fields.")
    public void loginWithEmptyFields() {
        // Perform login with empty fields and verify error messages
        login("", "");
        verifyEmptyFieldErrors();
    }

    @Test
    @Feature("Login Feature")
    @Description("Test Description: Verifying the login page's response to special characters in the email field.")
    public void loginWithSpecialCharactersInEmail() {
        // Perform login with special characters in email and verify unsuccessful login
        login("!@#$%^&*()_+[];',./", "Test@1234");
        verifyUnsuccessfulLogin();
    }

    @Test
    @Feature("Login Feature")
    @Description("Test Description: Verifying the login page's response to special characters in the password field.")
    public void loginWithSpecialCharactersInPassword() {
        // Perform login with special characters in password and verify unsuccessful login
        login("manreetkaur3018@gmail.com", "!@#$%^&*()_+[];',./");
        verifyUnsuccessfulLogin();
    }

    @Test
    @Feature("Login Feature")
    @Description("Test Description: Verifying the login page's response to both empty fields and special characters.")
    public void loginWithEmptyEmailAndSpecialPassword() {
        // Perform login with empty email and special characters in password, then verify errors
        login("", "!@#$%^&*()_+[];',./");
        verifyEmptyFieldErrors();
    }

    @Test
    @Feature("Login Feature")
    @Description("Test Description: Verifying the login page's response to special characters in email and empty password.")
    public void loginWithSpecialEmailAndEmptyPassword() {
        // Perform login with special characters in email and empty password, then verify errors
        login("!@#$%^&*()_+[];',./", "");
        verifyEmptyFieldErrors();  // Check for the empty password field error
    }

    @Step("Login with email: {email} and password: {password}")
    public void login(String email, String password) {
        // Locate the email and password fields and enter the provided credentials
        WebElement emailField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);

        // Locate the login button and click it
        WebElement loginButton = driver.findElement(By.xpath("//button[text()='Log In']")); // Corrected
        loginButton.click();
    }

    @Step("Verify successful login")
    public void verifySuccessfulLogin() {
        // Assert that the URL is correct for a successful login
        String expectedUrl = "https://app.germanyiscalling.com/cv/upload/";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
    }

    @Step("Verify unsuccessful login")
    public void verifyUnsuccessfulLogin() {
        // Assert that the error message is displayed for an unsuccessful login
        WebElement errorMessage = driver.findElement(By.xpath("//div[@class='alert alert-danger']")); // Corrected
        Assert.assertTrue(errorMessage.isDisplayed());
        Assert.assertEquals(errorMessage.getText(), "Please enter a correct username and password. Note that both fields may be case-sensitive.");
    }

    @Step("Verify empty field errors")
    public void verifyEmptyFieldErrors() {
        // Wait for the alert to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alertDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-danger']")));

        // Find all error messages within the alert
        List<WebElement> errorMessages = alertDiv.findElements(By.xpath(".//li"));

        // Print all error messages for debugging
        System.out.println("Error Messages Found:");
        for (WebElement errorMessage : errorMessages) {
            System.out.println(errorMessage.getText());
        }

        // Flags to check if specific error messages are displayed
        boolean emailErrorDisplayed = false;
        boolean passwordErrorDisplayed = false;

        // Check each error message for specific text
        for (WebElement errorMessage : errorMessages) {
            String errorText = errorMessage.getText();
            if (errorText.contains("Email: This field is required.")) {
                emailErrorDisplayed = true;
            }
            if (errorText.contains("Password: This field is required.")) {
                passwordErrorDisplayed = true;
            }
        }

        // Assert that at least one of the expected error messages is displayed
        if (!emailErrorDisplayed) {
            System.out.println("Email error message is not displayed.");
        }
        if (!passwordErrorDisplayed) {
            System.out.println("Password error message is not displayed.");
        }

        // Assert that at least one of the error messages is present
        Assert.assertTrue(emailErrorDisplayed || passwordErrorDisplayed, "At least one of the expected error messages is not displayed.");
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser and clean up resources
        if (driver != null) {
            driver.quit();
        }
    }
}
