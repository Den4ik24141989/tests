package pages;

import helpers.PageObjectUtils;
import helpers.Waiters;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class EstimatePage {

    /**
     * Browser driver.
     */
    private final WebDriver driver;

    /**
     * Page Url.
     * When creating an object, specify the id
     */
    public static final String PAGE_URL = "/estimates/";

    /**
     * Burger menu button.
     */
    @FindBy(id = "toggle-side-nav-button")
    private WebElement buttonMenu;

    /**
     * Button estimates in menu.
     */
    @FindBy(xpath = "//a[contains(text(), 'Оценки') "
            + "and contains(@class, 'active')]")
    private WebElement buttonEstimates;

    /**
     * Pop-up menu.
     */
    @FindBy(xpath = "//md-content[@width='4']")
    private WebElement menu;

    /**
     * Button add phase.
     */
    @FindBy(xpath = "//md-icon[contains(@aria-label, 'Add Phase')]")
    private WebElement buttonAddPhase;

    /**
     * Dialog window add phase.
     */
    @FindBy(xpath = "//md-dialog[contains(@class, 'phaseModal')]")
    private WebElement dialogWindowAddPhase;

    /**
     * Checkbox mobile app in dialog window add phase.
     */
    @FindBy(xpath = "//md-checkbox[contains"
            + "(@aria-label, 'Мобильное приложение')]")
    private WebElement checkboxMobileAppPhase;

    /**
     * Button save phase in dialog window add phase.
     */
    @FindBy(xpath = "//span[contains(text(), 'Сохранить')]")
    private WebElement buttonSavePhase;

    /**
     * Custom phase input field.
     */
    @FindBy(xpath = "//input[contains(@aria-label, 'Введите название фазы')]")
    private WebElement customPhaseInputField;

    /**
     * List phases.
     */
    @FindBy(xpath = "//label[contains(@class, 'phase-name')]")
    private List<WebElement> listPhases;

    /**
     * Button phases edit.
     */
    @FindBy(id = "edit-phase-button")
    private WebElement buttonEditPhases;

    /**
     * List phases delete.
     */
    @FindBy(id = "delete-phase-button")
    private List<WebElement> listPhasesDelete;

    /**
     * Delete dialog.
     */
    @FindBy(xpath = "//md-dialog[contains(@aria-label, 'Внимание!')]")
    private WebElement deleteDialog;

    /**
     * Delete dialog buttons.
     */
    @FindBy(xpath = "//md-dialog[contains(@aria-label, 'Внимание!')]"
            + "//button")
    private List<WebElement> buttonsDeleteDialog;

    /**
     * Focus button.
     */
    @FindBy(xpath = "//button[contains(@class, 'md-focused')]")
    private WebElement focusButton;

    /**
     * Save phase button.
     */
    @FindBy(id = "save-phase-button")
    private WebElement savePhaseButton;

    /**
     * Tooltip.
     */
    @FindBy(xpath = "//md-tooltip[@role='tooltip']")
    private WebElement tooltip;

    /**
     * Page object constructor. Checks that page is open when created.
     * @param webDriver browser driver
     * @throws IllegalStateException if page is not open now
     */
    public EstimatePage(final WebDriver webDriver)
            throws IllegalStateException {
        this.driver = webDriver;
        PageObjectUtils.waitPageLoad(driver, PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    /**
     * Menu button click.
     */
    @Step("Нажать на кнопку 'Меню-бургер'")
    public void clickMenu() {
        Waiters.waitUntilElementToBeClickable(driver, buttonMenu)
                .click();
    }

    /**
     * Get WebElement pop-up menu.
     * @return WebElement menu
     */
    @Step("Получить всплывающую панель меню")
    public WebElement getMenu() {
        return menu;
    }

    /**
     * In menu estimates button click.
     */
    @Step("Нажать на кнопку 'Оценки' в всплывающей панели меню")
    public void clickEstimates() {
        Waiters.waitUntilElementToBeClickable(driver, buttonEstimates)
                .click();
    }

    /**
     * Add phase button click.
     */
    @Step("Нажать на кнопку 'Добавить фазу'")
    public void addPhaseButtonClick() {
        Waiters.waitUntilElementToBeClickable(driver, buttonAddPhase)
                .click();
    }


    /**
     * Dialog window add phase is open.
     * @return boolean is open dialog window add phase
     */
    @Step("Окно добавления фазы открылось")
    public boolean isOpenDialogWindowAddPhase() {
        return Waiters
                .waitUntilElementVisibilityOf(driver, dialogWindowAddPhase);
    }

    /**
     * Checkbox mobile click in dialog window add phase.
     * @return name phase
     */
    @Step("Выбрать из справочника фазу 'Мобильное приложение'")
    public String checkboxMobileAppPhaseClick() {
        String namePhase = Waiters
                .waitUntilElementToBeClickable(driver, checkboxMobileAppPhase)
                .getText();
        checkboxMobileAppPhase.click();
        return namePhase;
    }

    /**
     * Send custom phase method.
     * @param namePhase name phase
     */
    @Step("Добавить новую кастомную фазу")
    public void sendPhase(final String namePhase) {
        customPhaseInputField.sendKeys(namePhase);
    }

    /**
     * Save button click in dialog window add phase.
     */
    @Step("Нажать кнопку 'Сохранить'")
    public void buttonSavePhaseClick() {
        buttonSavePhase.click();
    }

    /**
     * Save button click in dialog window add phase.
     * @param phaseName phase name to search
     * @return boolean phase is found
     */
    @Step("Добавленная фаза {phaseName} имеется")
    public boolean checkAddPhase(final String phaseName) {
        return Waiters.waitForTextInListElement(driver, listPhases,
                phaseName.toUpperCase());
    }

    /**
     * Click on first estimate.
     */
    @Step("Выбрать первую в списке фазу")
    public void clickOnFirstPhase() {
        listPhases.get(0).click();
    }

    /**
     * Click edit phase.
     */
    @Step("Нажать кнопку 'Изменить'")
    public void editPhaseClick() {
        buttonEditPhases.click();
    }

    /**
     * A cross appears above the name of each phase.
     * @return boolean
     */
    @Step("Над названием каждой из фаз появился крестик")
    public boolean checkDeleteButtons() {
        List<WebElement> phasesDelete = listPhasesDelete;

        for (int i = 0; i < listPhasesDelete.size() / 2; i++) {
            if (!Waiters.waitUntilElementVisibilityOf(driver,
                    phasesDelete.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check that the prompt appears.
     * @return boolean appears or not
     */
    @Step("При наведении на кнопку удаления появляется подсказка 'Удалить'")
    public boolean checkTooltip() {
        WebElement element = listPhasesDelete.get(0);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        return Waiters.waitUntilTextToBePresentInElement(driver, tooltip,
                "Удалить фазу");
    }

    /**
     * Click delete first phase.
     * @return WebElement to be removed
     */
    @Step("Нажать 'Удалить фазу' над первой в списке фазой")
    public WebElement deleteFirstPhaseClick() {
        WebElement phase = listPhases.get(0);
        listPhasesDelete.get(0).click();
        return phase;
    }

    /**
     * Check phase deletion confirmation window.
     * @return boolean matches or not
     */
    @Step("Окно подтверждения удаления фазы. Варианты ответа: "
            + "'ОТМЕНА' и 'ОК'. 'ОК' подсвечен как выбранный.")
    public boolean checkDeleteWindow() {
        List<WebElement> buttons = buttonsDeleteDialog;
        if (buttons.size() != 2) {
            return false;
        }
        return buttons.get(0).getText().equals("ОТМЕНА")
                && buttons.get(1).getText().equals("ОК")
                && buttons.get(1).getText().equals(focusButton.getText());
    }

    /**
     * Click 'OK' button.
     */
    @Step("Нажать 'ОК'")
    public void okClickInDeleteDialog() {
        Waiters
                .waitUntilElementToBeClickable(driver,
                        buttonsDeleteDialog.get(1))
                .click();
    }

    /**
     * Check that the phase has been removed.
     * @param deletePhase phase removed
     * @return boolean phase removed
     */
    @Step("Проверить что фаза удалена")
    public boolean checkDeletePhase(final WebElement deletePhase) {
        return Waiters.waitUntilElementInvisibilityOf(driver, deletePhase);
    }

    /**
     * Save phase button click.
     */
    @Step("Нажать кнопку 'Сохранить' справа от списка фаз")
    public void savePhaseButtonClick() {
        savePhaseButton.click();
    }

    /**
     * Button save is invisible.
     * @return button save is invisible
     */
    @Step("Кнопка 'Сохранить' исчезла")
    public boolean checkNotSavePhaseButton() {
        Waiters.waitUntilElementVisibilityOf(driver, savePhaseButton);
        Waiters.waitUntilElementVisibilityOf(driver, buttonAddPhase);
        return !savePhaseButton.isDisplayed()
                && buttonAddPhase.isDisplayed();
    }
}
