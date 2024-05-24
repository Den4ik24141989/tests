package tests.ui;

import helpers.ParametersProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import listeners.TestListener;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.EstimatePage;
import pages.EstimatesPage;
import pages.LoginPage;

import java.io.IOException;

@Listeners(TestListener.class)
@Epic("Управление фазами")
public class PhaseControlTests extends BaseTest {

    /**
     * Estimates page.
     */
    private EstimatePage estimatePage;

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
            estimatePage = new EstimatePage(getDriver());
    }

    /**
     * Test case QAA-7.
     */
    @Feature("Добавление фазы")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-7:Добавление фазы из справочника")
    public final void addPhaseDirectory() {
        estimatePage.addPhaseButtonClick();

        Assert.assertTrue(estimatePage.isOpenDialogWindowAddPhase(),
                "Окно добавления фазы не открылось");

        String namePhase = estimatePage.checkboxMobileAppPhaseClick();
        estimatePage.buttonSavePhaseClick();

        Assert.assertTrue(estimatePage
                        .checkAddPhase(namePhase),
                "Добавленная фаза отсутствует");
    }

    /**
     * Test case QAA-8.
     */
    @Feature("Добавление фазы")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-8:Добавление кастомной фазы не из справочника")
    public final void addPhaseCustom() {
        estimatePage.addPhaseButtonClick();

        Assert.assertTrue(estimatePage.isOpenDialogWindowAddPhase(),
                "Окно добавления фазы не открылось");

        String namePhase = "Новая фаза";
        estimatePage.sendPhase(namePhase);
        estimatePage.buttonSavePhaseClick();

        Assert.assertTrue(estimatePage
                        .checkAddPhase(namePhase),
                "Добавленная фаза отсутствует");
    }

    /**
     * Test case QAA-20.
     */
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-20:Удаление фазы")
    public final void deletePhase() {
        estimatePage.editPhaseClick();
        Assert.assertTrue(estimatePage.checkDeleteButtons(),
                "Кнопки удаления над фазами не появились");

        Assert.assertTrue(estimatePage.checkTooltip(),
                "Всплывающая подсказка не соответствует или не появилась");

        WebElement deletePhase = estimatePage.deleteFirstPhaseClick();
        Assert.assertTrue(estimatePage.checkDeleteWindow(),
                "Окно подтверждения не соответствует");

        estimatePage.okClickInDeleteDialog();

        Assert.assertTrue(estimatePage.checkDeletePhase(deletePhase),
                "Фаза не удалилась");

        estimatePage.savePhaseButtonClick();

        Assert.assertTrue(estimatePage.checkNotSavePhaseButton(),
                "Кнопка сохранить не исчезла");
    }
}
