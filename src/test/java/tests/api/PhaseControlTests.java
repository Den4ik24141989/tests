package tests.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.mapper.ObjectMapperType;
import models.CreatePhaseDTO;
import models.Estimate;
import models.Phase;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Управление фазами")
public class PhaseControlTests extends BaseApi {

    /**
     * Test case QAA-7.
     */
    @Feature("Добавление фазы")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-7:Добавление фазы из справочника")
    public final void addPhaseDirectory() {
        CreatePhaseDTO createPhaseDTO = new CreatePhaseDTO();
        createPhaseDTO.addTemplate(PHASE_MOBILE_APP);

        String estimateId = createEstimateId();

        Phase phase = addPhase(createPhaseDTO, estimateId)
                .then().assertThat()
                .statusCode(STATUS_OK)
                .extract()
                .as(Phase[].class, ObjectMapperType.GSON)[0];

        Estimate estimate = getEstimate(estimateId)
                .as(Estimate.class, ObjectMapperType.GSON);

        Assert.assertTrue(estimate.getPhases().contains(phase),
                "Фаза не добавилась");
        Assert.assertEquals(estimate.getPhases().get(0).getName(),
                createPhaseDTO.getName(),
                "Название фазы не соответствует");
    }

    /**
     * Test case QAA-8.
     */
    @Feature("Добавление фазы")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-8:Добавление кастомной фазы не из справочника")
    public final void addPhaseCustom() {
        CreatePhaseDTO phaseDTO = new CreatePhaseDTO();
        phaseDTO.addCustomNames("Своя фаза");

        String estimateId = createEstimateId();

        Phase phase = addPhase(phaseDTO, estimateId)
                .then().assertThat()
                .statusCode(STATUS_OK)
                .extract()
                .as(Phase[].class, ObjectMapperType.GSON)[0];

        Estimate estimate = getEstimate(estimateId)
                .as(Estimate.class, ObjectMapperType.GSON);

        Assert.assertTrue(estimate.getPhases().contains(phase),
                "Фаза не добавилась");
        Assert.assertEquals(estimate.getPhases().get(0).getName(),
                phaseDTO.getName(),
                "Название фазы не соответствует");
    }

    /**
     * Test case QAA-20.
     */
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-20:Удаление фазы")
    public final void deletePhaseTest() {
        String estimateId = createEstimateId();

        CreatePhaseDTO phaseDTO = new CreatePhaseDTO();
        phaseDTO.addCustomNames("Своя фаза");

        Phase phase = addPhase(phaseDTO, estimateId)
                .as(Phase[].class, ObjectMapperType.GSON)[0];

        deletePhase(estimateId, phase.getId())
                .then()
                .statusCode(STATUS_OK);

        Estimate estimate = getEstimate(estimateId)
                .as(Estimate.class, ObjectMapperType.GSON);

        Assert.assertFalse(estimate.getPhases().contains(phase),
                "Фаза не удалилась");
    }
}
