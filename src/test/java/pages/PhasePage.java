package pages;

import helpers.PageObjectUtils;
import helpers.Waiters;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Authorization page object.
 */
public class PhasePage {

    /**
     * Browser driver.
     */
    private final WebDriver driver;

    /**
     * Page url.
     */
    private static final String PAGE_URL = "/phases/";

    /**
     * Xpath task comment.
     */
    private static final String COMMENT_TASK =
            ".//md-icon[contains(@aria-label, 'Комментарий')]";

    /**
     * Button add task to phase.
     */
    @FindBy(xpath = "//button[contains(@class, 'fab-button')]")
    private WebElement buttonAddTaskToPhase;

    /**
     * Window for add task to phase.
     */
    @FindBy(xpath = "//div[contains(@class, 'whiteframe')]")
    private WebElement windowAddingTaskOrFeature;

    /**
     * Button add new task in window for add task to phase.
     */
    @FindBy(xpath = "//div[contains(@ng-click, 'addNewTask')]")
    private WebElement buttonAddNewTask;

    /**
     * Button add new feature in window for add task to phase.
     */
    @FindBy(xpath = "//div[contains(@ng-click, 'addNewFeature')]")
    private WebElement buttonAddNewFeature;

    /**
     * Window for add task or feature to phase.
     */
    @FindBy(xpath = "//md-dialog[contains(@class, 'addingModal')]")
    private WebElement addingModalWindow;

    /**
     * List task in modal window new task.
     */
    @FindBy(xpath = "//div[@class='name ng-binding']")
    private List<WebElement> listTaskInCreateNewTask;

    /**
     * Button save in window for add new task.
     */
    @FindBy(xpath = "//span[contains(text(), 'Сохранить')]")
    private WebElement buttonSaveInWindowForAddNewTask;

    /**
     * List task in phase.
     */
    @FindBy(xpath = "//div[contains(@class, 'task drag-wrapper')]")
    private List<WebElement> listTask;

    /**
     * List task in phase.
     */
    @FindBy(id = "min-hours-input")
    private List<WebElement> listTaskTimeFrom;

    /**
     * List task in phase.
     */
    @FindBy(id = "max-hours-input")
    private List<WebElement> listTaskTimeUntil;

    /**
     * List task name.
     */
    @FindBy(xpath = "//textarea[contains(@aria-label, 'Название таски')]")
    private List<WebElement> listTaskName;

    /**
     * List feature name.
     */
    @FindBy(xpath = "//textarea[contains(@aria-label, 'Название фичи')]")
    private List<WebElement> listFeatureName;

    /**
     * Input field comment.
     */
    @FindBy(xpath = "//textarea[@id='description-textarea']")
    private WebElement fieldComment;

    /**
     * Input field feature name.
     */
    @FindBy(id = "feature-name-textarea")
    private WebElement fieldFeatureName;

    /**
     * Progress line element.
     */
    @FindBy(xpath = "//md-progress-linear")
    private WebElement progressLine;

    /**
     * Total time from.
     */
    @FindBy(xpath = "//div[contains(@class,'phase-total-time')]/div[1]")
    private WebElement totalTimeFrom;

    /**
     * Total time until.
     */
    @FindBy(xpath = "//div[contains(@class,'phase-total-time')]/div[3]")
    private WebElement totalTimeUntil;

    /**
     * Empty space for click.
     */
    @FindBy(xpath = "//div[contains(@ui-view, 'phase')]")
    private WebElement emptySpaceForFlick;

    /**
     * Page object constructor. Checks that page is open when created.
     * @param webDriver browser driver
     * @throws IllegalStateException if page is not open now
     */
    public PhasePage(final WebDriver webDriver) throws
            IllegalStateException {
        this.driver = webDriver;
        PageObjectUtils.waitPageLoad(driver, PAGE_URL);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Click button for window add task to phase.
     */
    @Step("Нажать на кнопку 'Добавить' в правой нижней части страницы")
    public void buttonAddTaskToPhaseClick() {
        Waiters.waitUntilElementToBeClickable(driver, buttonAddTaskToPhase)
                .click();
    }

    /**
     * Click button add new task.
     */
    @Step("Нажать на кнопку 'Добавить новую задачу' в окне добавления задачи")
    public void addNewTaskClick() {
        Waiters.waitUntilElementToBeClickable(driver, buttonAddNewTask)
                .click();
    }

    /**
     * Click button add new feature.
     */
    @Step("Нажать на кнопку 'Добавить новую фичу' в окне добавления задачи")
    public void addNewFeatureClick() {
        Waiters.waitUntilElementToBeClickable(driver, buttonAddNewFeature)
                .click();
    }

    /**
     * Check that the window for adding a new task is open.
     * @return open or not
     */
    @Step("Проверить что окно добавления новой задачи открыто")
    public boolean isOpenAddingModalWindow() {
        return Waiters
                .waitUntilElementVisibilityOf(driver,
                        addingModalWindow);
    }

    /**
     * Select a task from the list by key word.
     * @param keyWord key word contained in the task
     */
    @Step("Выбрать задачу из списка")
    public void selectTask(final String keyWord) {
        Waiters.waitWebElementFromListContainingText(
                driver, listTaskInCreateNewTask, keyWord);
        listTaskInCreateNewTask.stream()
                .filter(el -> el.getText().toLowerCase()
                        .contains(keyWord.toLowerCase()))
                .findFirst().get()
                .click();
    }

    /**
     * Save click.
     */
    @Step("Нажать сохранить")
    public void saveButtonClick() {
        buttonSaveInWindowForAddNewTask.click();
    }

    /**
     * Check a task from the list by key word.
     * @param keyWord key word contained in the task
     * @return present or not
     */
    @Step("проверить наличие добавленной задачи")
    public boolean checkTaskInPhase(final String keyWord) {
        return Waiters.waitWebElementFromListContainingText(
                driver, listTaskName, keyWord);
    }

    /**
     * Check feature in phase.
     * @param keyWord key word search
     * @return present or not
     */
    @Step("проверить наличие добавленной фичи")
    public boolean checkFeatureInPhase(final String keyWord) {
        return Waiters.waitWebElementFromListContainingText(
                driver, listFeatureName, keyWord);
    }

    /**
     * Get list tasks.
     * @return list task web elements
     */
    public List<WebElement> getListTask() {
        return listTask;
    }

    /**
     * Click on comment in first task.
     * @param task task web element
     */
    @Step("Нажать на кнопку 'Комментарий' у первой в списке задачи")
    public void clickOnCommentInTask(final WebElement task) {
        task.findElement(By.xpath(COMMENT_TASK)).click();
    }

    /**
     * Click on comment in first task.
     * @return input line is there an or not
     */
    @Step("Под именем задачи появилась строка ввода текста")
    public boolean commentFieldAppeared() {
        return Waiters
                .waitUntilElementVisibilityOf(driver, fieldComment);
    }

    /**
     * Send comment method.
     * @param comment comment to input
     */
    @Step("Ввести описание задачи")
    public void sendComment(final String comment) {
        fieldComment.sendKeys(comment);
    }

    /**
     * Send feature name method.
     * @param featureName feature name to input
     */
    @Step("Ввести описание задачи")
    public void sendFeatureName(final String featureName) {
        fieldFeatureName.sendKeys(featureName);
    }

    /**
     * Get comment method.
     * @return WebElement field comment
     */
    public WebElement getFieldComment() {
        return fieldComment;
    }

    /**
     * Click in on empty space.
     */
    @Step("Нажать на пустое место")
    public void clickOnEmptySpace() {
        emptySpaceForFlick.click();
        Waiters.waitUntilGetCssValueEquals(driver,
                progressLine, "opacity", "0");
    }

    /**
     * Send hours time from in first task.
     * @param hours hours to input
     * @return web element where the time is set
     */
    @Step("Кликнуть по счетчику в колонке 'Часы: от' первой задачи в списке "
            + "и ввести новое значение счетчика.")
    public WebElement setTimeFromInFirstTask(final String hours) {
        WebElement timeFromFirstTask = listTaskTimeFrom.get(0);
        Waiters.waitUntilElementToBeClickable(driver, timeFromFirstTask)
                .click();
        timeFromFirstTask.sendKeys(hours);

        return timeFromFirstTask;
    }

    /**
     * Send hours time until in first task.
     * @param hours hours to input
     * @return web element where the time is set
     */
    @Step("Кликнуть по счетчику в колонке 'Часы: до' первой задачи в списке "
            + "и ввести новое значение счетчика.")
    public WebElement setTimeUntilInFirstTask(final String hours) {
        WebElement timeUntilFirstTask = listTaskTimeUntil.get(0);
        Waiters.waitUntilElementToBeClickable(driver, timeUntilFirstTask)
                .click();
        timeUntilFirstTask.sendKeys(hours);

        return timeUntilFirstTask;
    }

    /**
     * Check that the counter has recorded a value.
     * @param element set time element
     * @param setTime previously set time
     * @return matches or not
     */
    @Step("Счетчик зафиксировал значение")
    public boolean checkCounterFixedValue(final WebElement element,
                                          final String setTime) {
        return element.getAttribute("value").equals(setTime);
    }

    /**
     * Check the cursor has disappeared from the counter line.
     * @param element set time element
     * @return yes or not
     */
    @Step("Курсор исчез из строки счетчика.")
    public boolean checkCursorIsNotOnElement(final WebElement element) {
        return !element.equals(driver.switchTo().activeElement());
    }

    /**
     * Check the cursor has disappeared from the counter line.
     * @param list list elements time from or until
     * @return total time including bugs
     */
    @Step("Второй счетчик поля 'Часы фазы' (в нижней части экрана) "
            + "должен измениться на введенную величину.")
    public Double calculateTotalTimeIncludingBugs(final List<WebElement> list) {
        final double rounding = 10.0;
        final double bugsPercentage = 1.15;
        double sum = 0;
        for (WebElement element : list) {
            double increasedValue = Double
                    .parseDouble(element.getAttribute("value"))
                    * bugsPercentage;
            double roundedValue = Math.round(increasedValue * rounding)
                    / rounding;
            sum += roundedValue;
        }

        return Math.round(sum * rounding) / rounding;
    }

    /**
     * Get list web elements task time from.
     * @return list web elements task time from
     */
    public List<WebElement> getListTaskTimeFrom() {
        return listTaskTimeFrom;
    }

    /**
     * Get list web elements task time until.
     * @return list web elements task time until
     */
    public List<WebElement> getListTaskTimeUntil() {
        return listTaskTimeUntil;
    }

    /**
     * Get total time from.
     * @return total time from
     */
    public String getTotalTimeFrom() {
        return totalTimeFrom.getText();
    }

    /**
     * Get total time until.
     * @return total time until
     */
    public String getTotalTimeUntil() {
        return totalTimeUntil.getText();
    }
}
