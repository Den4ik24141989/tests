package pages;

import helpers.PageObjectUtils;
import helpers.Waiters;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Estimates list page object.
 */
public class EstimatesPage {

    /**
     * Browser driver.
     */
    private final WebDriver driver;

    /**
     * Page Url.
     */
    public static final String PAGE_URL = "/estimates";

    /**
     * Button plus, createEstimate.
     */
    @FindBy(xpath = "//button[@ng-Click='vm.openFab($event)']")
    private WebElement buttonCreateEstimate;

    /**
     * Estimate Button Pop-up Window.
     */
    @FindBy(xpath = "//div[@ng-if='vm.isOpen']")
    private WebElement popUpWindowCreateEstimate;

    /**
     * Button for selecting Russian estimate language.
     */
    @FindBy(xpath = "//div[@class='adding-area-item'"
            + "and contains(text(), 'Для русскоязычного клиента')]")
    private WebElement buttonRusClientEstimate;

    /**
     * List Estimates.
     */
    @FindBy(xpath = "//a[contains(@class, 'task-item')]")
    private List<WebElement> listEstimates;

    /**
     * List buttons delete estimate.
     */
    @FindBy(xpath = "//descendant::button[contains(@aria-label, 'Удалить')]")
    private List<WebElement> listButtonsDeleteEstimate;

    /**
     * Delete dialog.
     */
    @FindBy(xpath = "//md-dialog[contains(@aria-label, 'Внимание!')]")
    private WebElement deleteDialog;

    /**
     * List buttons in delete dialog.
     */
    @FindBy(xpath = "//md-dialog[contains(@aria-label, 'Внимание!')]"
            + "//button[contains(@class, 'md-button')]")
    private List<WebElement> listButtonsInDeleteDialog;

    /**
     * Focus button.
     */
    @FindBy(xpath = "//button[contains(@class, 'md-focused')]")
    private WebElement focusButton;

    /**
     * Page object constructor. Checks that page is open when created.
     * @param webDriver browser driver
     * @throws IllegalStateException if page is not open now
     */
    public EstimatesPage(final WebDriver webDriver) throws
            IllegalStateException {
        this.driver = webDriver;
        PageObjectUtils.waitPageLoad(driver, PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    /**
     * CreateEstimate button click.
     */
    @Step("Нажать на кнопку '+' в правом нижнем углу экрана")
    public void clickCreateEstimate() {
        buttonCreateEstimate.click();
    }

    /**
     * Get pop-up WebElement CreateEstimate language.
     * @return WebElement pop-up window create estimate
     */
    @Step("Получить всплывающее окно с выбором языка")
    public WebElement getPopUpWindowCreateEstimate() {
        return popUpWindowCreateEstimate;
    }

    /**
     * RusClientEstimate button click.
     */
    @Step("Нажать на кнопку с русским языком")
    public void clickRusClientEstimate() {
        buttonRusClientEstimate.click();
    }

    /**
     * Get list estimates.
     * @param href href estimate
     * @return estimate is created or not
     */
    @Step("Созданная оценка есть в списке")
    public boolean checkContainsEstimate(final String href) {

        return listEstimates.stream()
                .filter(estimate -> estimate.getAttribute("href")
                        .contains(href))
                .findFirst()
                .orElse(null) != null;
    }

    /**
     * Get list estimates.
     * @param href href estimate
     * @return estimate deleted or not
     */
    @Step("Удаленная оценка отсутствует в списке")
    public boolean checkNotContainsEstimate(final String href) {

        return Waiters.waitUntilElementWithAttributeDisappears(
                driver, listEstimates, "href", href);
    }

    /**
     * Click delete estimate.
     * @return name of the project to be deleted
     */
    @Step("Нажать удалить оценку первую в списке")
    public String deleteFirstEstimateClick() {
        String estimate = listEstimates.get(0).getAttribute("href");
        listButtonsDeleteEstimate.get(0).click();

        return estimate;
    }

    /**
     * Check availability delete estimate dialog.
     * @return availability delete estimate dialog
     */
    @Step("Открылось окно подтверждения удаления оценки"
            + " с вариантами ответа 'Отмена' и 'ОК'")
    public boolean checkDeleteEstimateDialog() {
        return Waiters.waitUntilElementVisibilityOf(driver, deleteDialog)
                && listButtonsInDeleteDialog.get(0).getText().equals("ОТМЕНА")
                && listButtonsInDeleteDialog.get(1).getText().equals("ОК");
    }

    /**
     * Check 'OK' button is highlighted as active.
     * @return button active or not
     */
    @Step("Кнопка 'ОК' подсвечена как активная")
    public boolean checkButtonOKHighlighted() {
        WebElement buttonOk = listButtonsInDeleteDialog.get(1);
        return buttonOk.getText().equals(focusButton.getText());
    }

    /**
     * Click confirm deletion of estimate.
     */
    @Step("Подтвердить удаление оценки")
    public void confirmDeletionOfEstimateClick() {
        listButtonsInDeleteDialog.get(1).click();
    }

    /**
     * Click on first estimate.
     */
    @Step("Выбрать первую в списке оценку")
    public void clickOnFirstEstimate() {
        listEstimates.get(0).click();
    }
}
