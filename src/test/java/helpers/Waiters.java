package helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

/**
 * Helper class for custom explicit waits.
 */
public final class Waiters {
    /**
     * Not called.
     */
    private Waiters() {
    }

    /**
     * Wait for Angular pending requests to finish.
     * @param webDriver browser driver
     * @param timeout wait threshold
     */
    public static void waitUntilAngularReady(
            final WebDriver webDriver, final int timeout) {
        final String angularReady =
                "return angular.element(document).injector()"
                        + ".get('$http').pendingRequests.length === 0";
        ExpectedCondition<Boolean> angularLoad = driver ->
                Boolean.valueOf(((JavascriptExecutor) driver)
                        .executeScript(angularReady).toString());
        new WebDriverWait(webDriver, timeout).until(angularLoad);
    }

    /**
     * Wait for Angular pending requests to finish.
     * @param webDriver browser driver
     * @throws IOException when config file is not available
     */
    public static void waitUntilAngularReady(final WebDriver webDriver)
            throws IOException {
        int explicitTimeout = Integer.parseInt(ParametersProvider
                .getProperty("explicitTimeout"));
        waitUntilAngularReady(webDriver, explicitTimeout);
    }

    /**
     * Wait element to be clickable.
     * @param webDriver browser driver
     * @param webElement webElement page
     * @return WebElement to be clickable
     */
    public static WebElement waitUntilElementToBeClickable(
            final WebDriver webDriver, final WebElement webElement) {
        int implicitTimeout;
        try {
            implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new WebDriverWait(webDriver, implicitTimeout)
                .until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Wait element to be visibility of.
     * @param webDriver browser driver
     * @param webElement webElement page
     * @return WebElement to be visibility
     */
    public static boolean waitUntilElementVisibilityOf(
            final WebDriver webDriver,
            final WebElement webElement) {
        int implicitTimeout;
        try {
            implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
            new WebDriverWait(webDriver, implicitTimeout)
                    .until(ExpectedConditions.visibilityOf(webElement));
            return true;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (TimeoutException timeEx) {
            return false;
        }
    }

    /**
     * Wait element to be invisibility of.
     * @param webDriver browser driver
     * @param webElement webElement page
     * @return boolean
     */
    public static boolean waitUntilElementInvisibilityOf(
            final WebDriver webDriver, final WebElement webElement) {
        int implicitTimeout;
        try {
            implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
            new WebDriverWait(webDriver, implicitTimeout)
                    .until(ExpectedConditions
                    .invisibilityOf(webElement));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException timeEx) {
            return false;
        }
    }

    /**
     * wait until get css value equals.
     * @param webDriver browser driver
     * @param webElement webElement page
     * @param cssValue css value attribute
     * @param equals the attribute must be equal to
     */
    public static void waitUntilGetCssValueEquals(
            final WebDriver webDriver,
            final WebElement webElement,
            final String cssValue,
            final String equals) {

        int implicitTimeout;
        try {
            implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
            new WebDriverWait(webDriver, implicitTimeout)
                    .until((ExpectedCondition<Boolean>) driver -> {
                        String attribute = webElement.getCssValue(cssValue);
                        return attribute.equals(equals);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException timeEx) {
            timeEx.printStackTrace();
        }
    }

    /**
     * Wait text to be present in element.
     * @param webDriver browser driver
     * @param webElement webElement page
     * @param text waiting for text
     * @return boolean result wait
     */
    public static boolean waitUntilTextToBePresentInElement(
            final WebDriver webDriver,
            final WebElement webElement,
            final String text) {
        int implicitTimeout;
        try {
            implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
            new WebDriverWait(webDriver, implicitTimeout)
                    .until(ExpectedConditions
                            .textToBePresentInElement(webElement, text));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException timeEx) {
            return false;
        }
    }

    /**
     * Wait for text in list element.
     * @param webDriver browser driver
     * @param webElements webElements list
     * @param text wait for text in list element
     * @return boolean result wait
     */
    public static boolean waitForTextInListElement(
            final WebDriver webDriver,
            final List<WebElement> webElements,
            final String text) {
        int implicitTimeout;
        try {
            implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
            new WebDriverWait(webDriver, implicitTimeout)
                    .until((ExpectedCondition<Boolean>) driver -> {
                        WebElement element = webElements.stream()
                                .filter(phase -> phase.getText()
                                        .equals(text)).findFirst()
                                .orElse(null);
                        return element != null;
                    });
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException timeEx) {
            return false;
        }
    }

    /**
     * Wait until element with attribute disappears.
     * @param webDriver browser driver
     * @param webElements webElements list
     * @param attr element attribute
     * @param text text contains
     * @return present or not
     */
    public static boolean waitUntilElementWithAttributeDisappears(
            final WebDriver webDriver,
            final List<WebElement> webElements,
            final String attr,
            final String text) {
        int implicitTimeout;
        try {
            implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
            new WebDriverWait(webDriver, implicitTimeout)
                    .until((ExpectedCondition<Boolean>) driver -> {
                        WebElement element = webElements.stream()
                                .filter(phase -> phase.getAttribute(attr)
                                        .contains(text)).findFirst()
                                .orElse(null);
                        return element == null;
                    });
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException timeEx) {
            return false;
        }
    }


    /**
     * Wait web element from list containing text.
     * @param webDriver browser driver
     * @param webElements webElements list
     * @param text text containing in web element
     * @return present or not
     */
    public static boolean waitWebElementFromListContainingText(
            final WebDriver webDriver,
            final List<WebElement> webElements,
            final String text) {
        int implicitTimeout;
        try {
            implicitTimeout = Integer.parseInt(ParametersProvider
                    .getProperty("implicitTimeout"));
            new WebDriverWait(webDriver, implicitTimeout)
                    .until((ExpectedCondition<Boolean>) driver -> {
                        WebElement element = webElements.stream()
                                .filter(task -> task.getText().toLowerCase()
                                        .contains(text.toLowerCase()))
                                .findFirst()
                                .orElse(null);
                        return element != null;
                    });
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException timeEx) {
            return false;
        }
    }
}
