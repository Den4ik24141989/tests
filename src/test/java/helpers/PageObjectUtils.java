package helpers;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

/**
 * Helper class for page objects.
 */
public final class PageObjectUtils {
    /**
     * Not called.
     */
    private PageObjectUtils() {
    }

    /**
     * Wait page is open by its Url.
     * @param driver browser driver
     * @param pageUrl page URL
     */
    public static void waitPageLoad(final WebDriver driver,
                                    final String pageUrl) {
        int timeout;
        try {
            timeout = Integer.parseInt(ParametersProvider
                    .getProperty("explicitTimeout"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Waiters.waitUntilAngularReady(driver, timeout);
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.urlContains(pageUrl));
    }

    /**
     * Checks that page is open by its Url.
     * @param driver browser driver
     * @param pageUrl page URL
     * @return is page open
     */
    public static boolean checkPageIsPresentByUrl(final WebDriver driver,
                                                  final String pageUrl) {
        try {
            waitPageLoad(driver, pageUrl);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * Adds a screenshot to the report allure.
     * @param driver browser driver
     */
    public static void takeToScreenshot(final WebDriver driver) {
         Allure.getLifecycle().addAttachment("screenshot", "image/png",
                "png", ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES));
    }
}
