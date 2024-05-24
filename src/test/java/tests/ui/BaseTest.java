package tests.ui;

import helpers.DriverFactory;
import helpers.ParametersProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    /**
     * Browser driver.
     */
    private WebDriver driver;

    /**
     * WebDriver setup.
     */
    @BeforeMethod
    public final void setDriver() throws IOException {
            int implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
            String webUrl = ParametersProvider.getProperty("webUrl");
            driver = DriverFactory.createDriver();
            driver.manage().timeouts()
                    .implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(webUrl);
    }

    /**
     * Get browser driver.
     * @return browser driver
     */
    public WebDriver getDriver() {
        return driver;
    }


    /**
     * Suite teardown.
     */
    @AfterMethod
    public final void tearDown() {
        getDriver().quit();
    }
}
