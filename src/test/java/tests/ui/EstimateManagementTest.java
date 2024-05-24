package tests.ui;

import helpers.PageObjectUtils;
import helpers.ParametersProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.EstimateCreatePage;
import pages.EstimatePage;
import pages.EstimatesPage;
import pages.LoginPage;

import java.io.IOException;

@Epic("Управление оценками")
@Listeners(TestListener.class)
public class EstimateManagementTest extends BaseTest {

    /**
     * Estimates page.
     */
    private EstimatesPage estimatesPage;

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
            estimatesPage = new LoginPage(getDriver())
                    .login(adminLogin, adminPassword);
    }

    /**
     * Test case QAA-5.
     */
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-5:Создание оценки")
    public final void createEstimate() {
        estimatesPage.clickCreateEstimate();
        Assert.assertTrue(estimatesPage.getPopUpWindowCreateEstimate()
                .isDisplayed());
        estimatesPage.clickRusClientEstimate();

        EstimateCreatePage estimateCreatePage =
                new EstimateCreatePage(getDriver());
        String estimateId = getDriver().getCurrentUrl()
                .replaceAll("http://localhost:8000/estimates/", "")
                .replaceAll("/edit", "");
        estimateCreatePage.sendClient("Новый клиент")
                .sendNameProject("Некий проект")
                .sendDescription("Некое описание")
                .sendLinkInCRM("linkInCRM")
                .sendManager("Иванов")
                .sendExpert("Иванов")
                .sendModerator("Иванов")
                .clickCheckboxDepartmentMobile()
                .clickCheckboxDepartmentDesign()
                .clickCheckboxDepartmentMobileQA()
                .clickRadioButtonLanguageRu()
                .clickCheckboxDepartmentMobile()
                .clickRadioButtonLanguageRu()
                .clickSaveAndAddPhase();
        Assert.assertTrue(estimateCreatePage.getDialogWindow(),
                "Всплывающее окно создания фазы не появилось");
        estimateCreatePage.closeDialogWindow();

        EstimatePage estimatePage = new EstimatePage(getDriver());
        Assert.assertTrue(PageObjectUtils
                        .checkPageIsPresentByUrl(getDriver(),
                                EstimatePage.PAGE_URL),
                "Открыта страница " + EstimatePage.PAGE_URL);
        estimatePage.clickMenu();
        Assert.assertTrue(estimatePage.getMenu().isDisplayed(),
                "В левой части экрана не появилась всплывающая панель меню");
        estimatePage.clickEstimates();

        Assert.assertTrue(PageObjectUtils
                        .checkPageIsPresentByUrl(getDriver(),
                                EstimatesPage.PAGE_URL),
                "Открыта страница " + EstimatesPage.PAGE_URL);
        Assert.assertTrue(estimatesPage.checkContainsEstimate(estimateId),
                "Созданная оценка отсутствует в списке");
    }

    /**
     * Test case QAA-6.
     */
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-6:Удаление оценки")
    public final void deleteEstimate() {
        String estimateHref = estimatesPage.deleteFirstEstimateClick();

        Assert.assertTrue(estimatesPage.checkDeleteEstimateDialog(),
                "Окно подтверждения не открылось");

        Assert.assertTrue(estimatesPage
                        .checkButtonOKHighlighted(),
                "Кнопка 'ОК' не подсвечена как активная");

        estimatesPage.confirmDeletionOfEstimateClick();

        Assert.assertTrue(estimatesPage.checkNotContainsEstimate(estimateHref),
                "Удаленная оценка присутствует в списке");
    }
}
