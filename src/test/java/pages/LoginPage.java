package pages;

import helpers.PageObjectUtils;
import helpers.Waiters;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

/**
 * Authorization page object.
 */
public class LoginPage {

    /**
     * Browser driver.
     */
    private final WebDriver driver;

    /**
     * Page url.
     */
    private static final String PAGE_URL = "/login";

    /**
     * Login input element.
     */
    @FindBy(css = "input[ng-model='vm.login']")
    private WebElement loginInput;

    /**
     * Password input element.
     */
    @FindBy(css = "input[ng-model='vm.password']")
    private WebElement passwordInput;

    /**
     * Login submit button.
     */
    @FindBy(css = "button[ng-click='vm.log()']")
    private WebElement loginButton;

    /**
     * Error message if the login is incorrect.
     */
    @FindBy(xpath = "//div[@class='error-msg ng-binding']")
    private WebElement errorMessage;

    /**
     * Page object constructor. Checks that page is open when created.
     * @param webDriver browser driver
     * @throws IllegalStateException if page is not open now
     */
    public LoginPage(final WebDriver webDriver) throws
            IllegalStateException {
        this.driver = webDriver;
        PageObjectUtils.waitPageLoad(driver, PAGE_URL);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Authorization method.
     * @param login    login to input
     * @param password password to input
     * @return page object with estimates list
     * @throws IOException when config file is not available
     */
    @Step("Ввести данные в поле 'Login', 'Password',"
            + "и нажать кнопку 'LOGIN'")
    public final EstimatesPage login(final String login,
                                     final String password)
            throws IOException {
        Waiters.waitUntilAngularReady(driver);
        sendLogin(login);
        sendPassword(password);
        loginButtonClick();
        return new EstimatesPage(driver);
    }

    /**
     * Send login method.
     * @param login login to input
     * @return this page
     */
    @Step("Ввести в поле 'Login'")
    public final LoginPage sendLogin(final String login) {
        loginInput.sendKeys(login);
        return this;
    }

    /**
     * Send password method.
     * @param password password to input
     * @return this page
     */
    @Step("Ввести в поле 'Password'")
    public final LoginPage sendPassword(final String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    /**
     * Login button click.
     * @return this page
     */
    @Step("Нажать на кнопку 'LOGIN'")
    public final LoginPage loginButtonClick() {
        loginButton.click();
        return this;
    }

    /**
     * @return WebElement login
     */
    @Step("Получить поле 'Login'")
    public WebElement getLoginInput() {
        return loginInput;
    }

    /**
     * @return WebElement password
     */
    @Step("Получить поле 'Password'")
    public WebElement getPasswordInput() {
        return passwordInput;
    }

    /**
     * @return WebElement login button
     */
    @Step("Получить кнопку 'Login'")
    public WebElement getLoginButton() {
        return loginButton;
    }

    /**
     * @return WebElement error message
     */
    @Step("Получить сообщение об ошибке")
    public WebElement getErrorMessage() {
        return errorMessage;
    }
}
