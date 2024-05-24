package tests.api;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

public class JsonSchemaValidationTest extends BaseApi {


    /**
     * Test case QAA-6126.
     */
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "QAA-6126:Создание оценки по API в EM")
    public void createEstimateTest() {
        createEstimate()
                .then().assertThat()
                .body(JsonSchemaValidator
                        .matchesJsonSchema(JSON_SCHEMA_CREATE_ESTIMATE));
    }
}
