package tests.ui;

import helpers.ParametersProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EstimatePage;
import pages.EstimatesPage;
import pages.LoginPage;
import pages.PhasePage;

import java.io.IOException;

@Epic("Управление задачами фаз")
public class PhaseTaskManagementTests extends BaseTest {

    /**
     * Phase page.
     */
    private PhasePage phasePage;

    /**
     * Possible rounding difference.
     */
    private static final double POSSIBLE_ROUNDING_DIFFERENCE = 0.1;

    /**
     * Suite setup.
     * @throws IOException when config file is not available
     */
    @BeforeMethod
    public final void setEnvironment() throws IOException {
        String adminLogin = ParametersProvider
                .getProperty("adminLogin");
        String adminPassword = ParametersProvider
                .getProperty("adminPassword");
        EstimatesPage estimatesPage = new LoginPage(getDriver())
                .login(adminLogin, adminPassword);
        estimatesPage.clickOnFirstEstimate();
        EstimatePage estimatePage = new EstimatePage(getDriver());
        estimatePage.clickOnFirstPhase();
        phasePage = new PhasePage(getDriver());
    }

    /**
     * Test case QAA-15.
     */
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-15:Добавление описания задачи")
    public final void addTaskDescription() {
        WebElement task = phasePage.getListTask().get(0);
        phasePage.clickOnCommentInTask(task);

        Assert.assertTrue(phasePage.commentFieldAppeared(),
                "Строка под именем задачи не появилась");

        String comment = "Новый комментарий";

        phasePage.sendComment(comment);

        phasePage.clickOnCommentInTask(task);
        phasePage.clickOnCommentInTask(task);

        Assert.assertEquals(phasePage.getFieldComment().getText(), comment,
                "Текст не соответствует введенному ранее");

        Assert.assertEquals(phasePage.getFieldComment()
                        .getAttribute("selectionStart"), "0",
                "Позиция мигающего курсора не соответствует началу строки");
    }

    /**
     * Test case QAA-16.
     */
    @Feature("Добавление задачи в фазу")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-16:Добавление задачи"
            + " в фазу вручную не из справочника")
    public final void addTaskToPhaseManually() {
        phasePage.buttonAddTaskToPhaseClick();
        phasePage.addNewFeatureClick();
        Assert.assertTrue(phasePage.isOpenAddingModalWindow(),
                "Окно добавления фичи не открылось");

        String featureName = "Свое название задачи";
        phasePage.sendFeatureName(featureName);
        phasePage.saveButtonClick();

        Assert.assertTrue(phasePage.checkFeatureInPhase(featureName),
                "Добавленная фича отсутствует в списке - " + featureName);
    }

    /**
     * Test case QAA-17.
     */
    @Feature("Добавление задачи в фазу")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-17:Добавление задачи в фазу из справочника")
    public final void addTaskToPhaseFromDirectory() {
        phasePage.buttonAddTaskToPhaseClick();
        phasePage.addNewTaskClick();
        Assert.assertTrue(phasePage.isOpenAddingModalWindow());

        String selectTask = "Реализация";
        phasePage.selectTask(selectTask);

        phasePage.saveButtonClick();
        Assert.assertTrue(phasePage.checkTaskInPhase(selectTask),
                "Добавленная задача отсутствует в списке - " + selectTask);
    }

    /**
     * Test case QAA-18.
     */
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-18:Добавление часов в поле 'ОТ'")
    public final void addHoursToTheFromField() {
        String setTime = "1.5";
        WebElement timeFromFirstTask = phasePage
                .setTimeFromInFirstTask(setTime);
        phasePage.clickOnEmptySpace();

        Assert.assertTrue(phasePage
                        .checkCounterFixedValue(timeFromFirstTask, setTime),
                "Счетчик не зафиксировал значение");

        Assert.assertTrue(phasePage
                        .checkCursorIsNotOnElement(timeFromFirstTask),
                "Курсор не исчез из строки счетчика");

        Assert.assertEquals(
                phasePage.calculateTotalTimeIncludingBugs(
                        phasePage.getListTaskTimeFrom()),
                Double.parseDouble(phasePage.getTotalTimeFrom()),
                POSSIBLE_ROUNDING_DIFFERENCE,
                "счетчик поля 'Часы фазы' (в нижней части экрана) "
                        + "не изменился на введенную величину");
    }

    /**
     * Test case QAA-19.
     */
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-19:Добавление часов в поле 'ДО'")
    public final void addHoursToTheUntilField() {
        String setTime = "5.5";
        WebElement timeUntilFirstTask = phasePage
                .setTimeUntilInFirstTask(setTime);
        phasePage.clickOnEmptySpace();

        Assert.assertTrue(phasePage
                        .checkCounterFixedValue(timeUntilFirstTask, setTime),
                "Счетчик не зафиксировал значение");

        Assert.assertTrue(phasePage
                        .checkCursorIsNotOnElement(timeUntilFirstTask),
                "Курсор не исчез из строки счетчика");

        Assert.assertEquals(
                phasePage.calculateTotalTimeIncludingBugs(
                        phasePage.getListTaskTimeUntil()),
                Double.parseDouble(phasePage.getTotalTimeUntil()),
                POSSIBLE_ROUNDING_DIFFERENCE,
                "счетчик поля 'Часы фазы' (в нижней части экрана) "
                        + "не изменился на введенную величину");
    }
}
