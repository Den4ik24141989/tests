package pages;

import helpers.PageObjectUtils;
import helpers.Waiters;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class EstimateCreatePage {

    /**
     * Browser driver.
     */
    private final WebDriver driver;

    /**
     * Page Url.
     */
    public static final String PAGE_URL = "/edit";

    /**
     * Client input element.
     */
    @FindBy(xpath = "//label[contains(text(), 'Клиент')]"
            + "/following-sibling::textarea")
    private WebElement fieldClient;

    /**
     * NameProject input element.
     */
    @FindBy(xpath = "//label[contains(text(), 'Название проекта')]"
            + "/following-sibling::textarea")
    private WebElement fieldNameProject;

    /**
     * Description input element.
     */
    @FindBy(xpath = "//label[contains(text(), 'Описание')]"
            + "/following-sibling::textarea")
    private WebElement fieldDescription;

    /**
     * Department mobile checkbox.
     */
    @FindBy(xpath = "//md-checkbox[@aria-label="
            + "'*Направление мобильной разработки']")
    private WebElement checkboxDepartmentMobile;

    /**
     * Department QA mobile checkbox.
     */
    @FindBy(xpath = "//md-checkbox[@aria-label='*Направление QA']")
    private WebElement checkboxDepartmentQA;

    /**
     * Department Design mobile checkbox.
     */
    @FindBy(xpath = "//md-checkbox[@aria-label='*Направление Дизайна']")
    private WebElement checkboxDepartmentDesign;

    /**
     * Expert input element.
     */
    @FindBy(xpath = "(//input[@type='search'])[1]")
    private WebElement fieldExpert;

    /**
     * Moderators input element.
     */
    @FindBy(xpath = "(//input[@type='search'])[2]")
    private WebElement fieldModerators;

    /**
     * Manager input element.
     */
    @FindBy(xpath = "(//input[@type='search'])[3]")
    private WebElement fieldManager;

    /**
     * Radio button Language ru.
     */
    @FindBy(xpath = "(//md-radio-button[@role='radio'])[1]")
    private WebElement radioButtonLanguageRu;

    /**
     * Radio button Language en.
     */
    @FindBy(xpath = "(//md-radio-button[@role='radio'])[2]")
    private WebElement radioButtonLanguageEn;

    /**
     * Link in CRM input element.
     */
    @FindBy(xpath = "//label[contains(text(), 'Ссылка в CRM')]"
            + "/following-sibling::textarea")
    private WebElement fieldLinkInCRM;

    /**
     * Button Language en.
     */
    @FindBy(id = "edit-about-button")
    private WebElement buttonSaveAndAddPhase;

    /**
     * Phase dialog window.
     */
    @FindBy(xpath = "//md-dialog[@role='dialog']")
    private WebElement dialogWindow;

    /**
     * Button phase dialog close.
     */
    @FindBy(xpath = "//md-icon[@ng-click='vm.closeModal()']")
    private WebElement buttonCloseDialogWindow;

    /**
     * Page object constructor. Checks that page is open when created.
     * @param webDriver browser driver
     * @throws IllegalStateException if page is not open now
     */
    public EstimateCreatePage(final WebDriver webDriver) throws
            IllegalStateException {
        this.driver = webDriver;
        PageObjectUtils.waitPageLoad(driver, PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    /**
     * Send client method.
     * @param client client to input
     * @return this page
     */
    @Step("Ввод в поле 'Клиент'")
    public EstimateCreatePage sendClient(final String client) {
        fieldClient.sendKeys(client);
        return this;
    }

    /**
     * Send name project method.
     * @param nameProject name project to input
     * @return this page
     */
    @Step("Ввод в поле 'Название проекта'")
    public EstimateCreatePage sendNameProject(final String nameProject) {
        fieldNameProject.sendKeys(nameProject);
        return this;
    }

    /**
     * Send description project method.
     * @param description description project to input
     * @return this page
     */
    @Step("Ввод в поле 'Описание'")
    public EstimateCreatePage sendDescription(final String description) {
        fieldDescription.sendKeys(description);
        return this;
    }

    /**
     * Department mobile checkbox click.
     * @return this page
     */
    @Step("Нажать на чекбокс 'Направление Мобильной разработки'")
    public EstimateCreatePage clickCheckboxDepartmentMobile() {
        checkboxDepartmentMobile.click();
        return this;
    }

    /**
     * Department QA checkbox click.
     * @return this page
     */
    @Step("Нажать на чекбокс 'Направление QA'")
    public EstimateCreatePage clickCheckboxDepartmentMobileQA() {
        checkboxDepartmentQA.click();
        return this;
    }

    /**
     * Department design checkbox click.
     * @return this page
     */
    @Step("Нажать на чекбокс 'Направление Дизайна'")
    public EstimateCreatePage clickCheckboxDepartmentDesign() {
        checkboxDepartmentDesign.click();
        return this;
    }

    /**
     * Send expert method.
     * @param expert expert to input
     * @return this page
     */
    @Step("Ввод в поле 'Эксперты', "
            + "выбрать первого из списка")
    public EstimateCreatePage sendExpert(final String expert) {
        fieldExpert.sendKeys(expert);
        fieldExpert.sendKeys(Keys.ARROW_DOWN);
        fieldExpert.sendKeys(Keys.ENTER);
        return this;
    }

    /**
     * Send moderator method.
     * @param moderator moderator to input
     * @return this page
     */
    @Step("Ввод в поле 'Модераторы', "
            + "выбрать первого из списка")
    public EstimateCreatePage sendModerator(final String moderator) {
        fieldModerators.sendKeys(moderator);
        fieldModerators.sendKeys(Keys.ARROW_DOWN);
        fieldModerators.sendKeys(Keys.ENTER);
        return this;
    }

    /**
     * Send manager method.
     * @param manager manager to input
     * @return this page
     */
    @Step("Ввод в поле 'Менеджер отдела продаж', "
            + "выбрать первого из списка")
    public EstimateCreatePage sendManager(final String manager) {
        fieldManager.sendKeys(manager);
        fieldManager.sendKeys(Keys.ARROW_DOWN);
        fieldManager.sendKeys(Keys.ENTER);
        return this;
    }

    /**
     * Radio button language ru click.
     * @return this page
     */
    @Step("Нажать в поле 'Языковые настройки оценки'"
            + " на радио кнопку с русским языком")
    public EstimateCreatePage clickRadioButtonLanguageRu() {
        radioButtonLanguageRu.click();
        return this;
    }

    /**
     * Radio button language en click.
     * @return this page
     */
    @Step("Нажать в поле 'Языковые настройки оценки'"
            + " на радио кнопку с английским языком")
    public EstimateCreatePage clickRadioButtonLanguageEn() {
        radioButtonLanguageEn.click();
        return this;
    }

    /**
     * Send link in CRM method.
     * @param linkInCRM link in CRM to input
     * @return this page
     */
    @Step("Ввод в поле 'Ссылка в CRM'")
    public EstimateCreatePage sendLinkInCRM(final String linkInCRM) {
        fieldLinkInCRM.sendKeys(linkInCRM);
        return this;
    }

    /**
     * Button save and add phase click.
     */
    @Step("Нажать на кнопку 'Сохранить и добавить фазу'")
    public void clickSaveAndAddPhase() {
        buttonSaveAndAddPhase.click();
    }

    /**
     * Button close dialog window click.
     */
    @Step("Закрыть всплывающее окно фазы")
    public void closeDialogWindow() {
        Waiters.waitUntilElementToBeClickable(driver, buttonCloseDialogWindow)
                .click();
    }

    /**
     * Get dialog window element.
     * @return WebElement dialogWindow to be visibility
     */
    @Step("Получить диалоговое окно фазы")
    public boolean getDialogWindow() {
        return Waiters.waitUntilElementVisibilityOf(driver, dialogWindow);
    }
}
