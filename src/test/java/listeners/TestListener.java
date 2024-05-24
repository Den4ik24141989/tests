package listeners;

import helpers.PageObjectUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;

public class TestListener implements ITestListener {
    @Override
    public final void onTestFailure(final ITestResult result) {
        try {
            Object currentClass = result.getInstance();
            Field field = currentClass.getClass().getDeclaredField("driver");
            field.setAccessible(true);
            WebDriver driver = (WebDriver) field.get(currentClass);
            PageObjectUtils.takeToScreenshot(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
