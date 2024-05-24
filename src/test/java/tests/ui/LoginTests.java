package tests.ui;

import helpers.PageObjectUtils;
import helpers.ParametersProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.EstimatesPage;
import pages.LoginPage;

import java.io.IOException;

/**
 * Test suite for authorization page.
 */
@Epic("Страница авторизации")
@Listeners(TestListener.class)
public class LoginTests extends BaseTest {

    /**
     * Test method parameters.
     * @return test data
     * @throws IOException when config file is not available
     */
    @DataProvider
    public Object[][] dataProviderPositiveAuth() throws IOException {
        return new Object[][]{
                {ParametersProvider.getProperty("adminLogin"),
                        ParametersProvider.getProperty("adminPassword")},
                {ParametersProvider.getProperty("moderatorLogin"),
                        ParametersProvider.getProperty("moderatorPassword")},
                {ParametersProvider.getProperty("estimatorLogin"),
                        ParametersProvider.getProperty("estimatorPassword")}
        };
    }

    /**
     * Test case EST-1.
     * @param login existing login
     * @param password valid password
     */
    @Story("EST-1:Авторизация с корректным логином и паролем")
    @Test(description = "EST-1:Авторизация с корректным логином и паролем",
            dataProvider = "dataProviderPositiveAuth")
    public final void checkCorrectLoginPassAuth(final String login,
                                                final String password) {
        new LoginPage(getDriver())
                .sendLogin(login)
                .sendPassword(password)
                .loginButtonClick();

        Assert.assertTrue(PageObjectUtils
                        .checkPageIsPresentByUrl(getDriver(),
                                EstimatesPage.PAGE_URL),
                "Открыта страница " + EstimatesPage.PAGE_URL);
    }

    /**
     * Test case QAA-2.
     */
    @Story("QAA-2:Авторизация с несуществующим логином и паролем")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "QAA-2:Авторизация с несуществующим логином и паролем")
    public final void checkNotCorrectLoginNotEmptyPassAuth() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.sendLogin("admin")
                .sendPassword("admin")
                .loginButtonClick();

        Assert.assertEquals(loginPage.getErrorMessage().getText(),
                "User not found");

        Assert.assertEquals(loginPage.getErrorMessage().getCssValue("color"),
                "rgba(221, 44, 0, 1)");
    }

    /**
     * Test case QAA-3.
     * @throws IOException when config file is not available
     */
    @Severity(SeverityLevel.CRITICAL)
    @Test(description =
            "QAA-3:Авторизация с корректным логином и пустым паролем")
    public final void checkCorrectLoginEmptyPassAuth() throws IOException {
        String adminLogin = ParametersProvider.getProperty("adminLogin");
        String adminPassword = "";
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.sendLogin(adminLogin)
                .sendPassword(adminPassword);
        String originalButtonColor = loginPage.getLoginButton()
                .getCssValue("background-color");
        loginPage.loginButtonClick();
        String pressedButtonColor = loginPage.getLoginButton()
                .getCssValue("background-color");

        Assert.assertNotEquals(originalButtonColor, pressedButtonColor,
                "Кнопка LOGIN визуально не активируется");

        Assert.assertFalse(PageObjectUtils
                        .checkPageIsPresentByUrl(getDriver(),
                                EstimatesPage.PAGE_URL),
                "Открыта страница " + EstimatesPage.PAGE_URL);

        Assert.assertEquals(loginPage.getLoginInput()
                        .getAttribute("value"), adminLogin,
                "поле Login содержит текст "
                        + loginPage.getLoginInput().getText());

        Assert.assertEquals(loginPage.getPasswordInput()
                        .getAttribute("value"), adminPassword,
                "поле Password содержит текст "
                        + loginPage.getLoginInput().getText());
    }

    /**
     * Test case QAA-4.
     */
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "QAA-4:Авторизация с пустыми логином и паролем")
    public final void checkEmptyLoginPassAuth() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.sendLogin("")
                .sendPassword("");
        String originalButtonColor = loginPage.getLoginButton()
                .getCssValue("background-color");
        loginPage.loginButtonClick();
        String pressedButtonColor = loginPage.getLoginButton()
                .getCssValue("background-color");

        Assert.assertNotEquals(originalButtonColor, pressedButtonColor,
                "Кнопка LOGIN визуально не активируется");

        Assert.assertEquals(loginPage.getLoginInput()
                        .getAttribute("value"), "",
                "поле Login содержит текст "
                        + loginPage.getLoginInput().getText());

        Assert.assertEquals(loginPage.getPasswordInput()
                        .getAttribute("value"), "",
                "поле Password содержит текст "
                        + loginPage.getLoginInput().getText());
    }
}
