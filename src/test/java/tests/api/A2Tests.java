package tests.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("А2")
public class A2Tests extends BaseApi {

    /**
     * Test case QAA-6126.
     */
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "QAA-6126:Создание оценки по API в EM")
    public void createEstimateTest() {
        createEstimate()
                .then().assertThat()
                .body(JsonSchemaValidator
                        .matchesJsonSchema(JSON_SCHEMA_ESTIMATE))
                .extract()
                .body()
                .jsonPath()
                .get("_id");
    }

    /**
     * Test case QAA-6134.
     */
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "QAA-6134:Обновление оценки по API в EM")
    public void editEstimate() {
        String createEstimateId = createEstimateId();

        given().body(JSON_EDIT_ESTIMATE)
                .header("Authorization", getToken())
                .pathParam("estimateId", createEstimateId)
                .put("api/estimates/{estimateId}")
                .then()
                .assertThat()
                .statusCode(STATUS_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchema(JSON_SCHEMA_ESTIMATE));

        JsonPath jsonPath = getEstimate(createEstimateId)
                .then()
                .assertThat()
                .statusCode(STATUS_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchema(JSON_SCHEMA_ESTIMATE))
                .extract().jsonPath();

        Assert.assertEquals(jsonPath.get("name"), "Измененная оценка",
                "Имя оценки не изменилось");
        Assert.assertEquals(jsonPath.get("customer"), "Новый клиент",
                "Имя клиента не изменилось");
    }

    /**
     * Test case QAA-6127.
     */
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "QAA-6127:Копирование оценки по API в EM")
    public void copyEstimate() {
        String createEstimateId = createEstimateId();

        String copyEstimateId = given().header("Authorization", getToken())
                .pathParam("estimateId", createEstimateId)
                .post("api/estimates/{estimateId}/copy")
                .then()
                .assertThat()
                .statusCode(STATUS_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchema(JSON_SCHEMA_ESTIMATE))
                .extract().jsonPath().get("_id");

        JsonPath jsonPath = getEstimate(copyEstimateId)
                .then()
                .assertThat()
                .statusCode(STATUS_OK)
                .body(JsonSchemaValidator
                        .matchesJsonSchema(JSON_SCHEMA_ESTIMATE))
                .extract().jsonPath();

        Assert.assertEquals(jsonPath.get("name"), "Новая оценка - копия",
                "Имя оценки не соответствует ожидаемому");
    }

    /**
     * Test case QAA-6128.
     */
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "QAA-6128:Удаление оценки по API в EM")
    public void deleteEstimate() {
        String createEstimateId = createEstimateId();

        given().header("Authorization", getToken())
                .pathParam("estimateId", createEstimateId)
                .delete("api/estimates/{estimateId}")
                .then().assertThat()
                .statusCode(STATUS_OK)
                .body(Matchers.equalTo(createEstimateId + " removed"));

        getEstimate(createEstimateId)
                .then().assertThat()
                .statusCode(STATUS_SERVER_ERROR);
    }

    /**
     * Test case QAA-6135.
     */
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "QAA-6135:Создание оценки по API в EM без авторизации")
    public void negativeCreateEstimateNoAuth() {
        given().body(JSON_CREATE_ESTIMATE)
                .post("api/estimates")
                .then().assertThat()
                .statusCode(STATUS_UNAUTHORIZED)
                .body(Matchers.equalTo("Login first"));
    }

    /**
     * Test case QAA-6136.
     */
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "QAA-6136:Обновление оценки по API"
            + " в EM без авторизации")
    public void negativeUpdateEstimateNoAuth() {
        String createEstimateId = createEstimateId();

        given().body(JSON_EDIT_ESTIMATE)
                .pathParam("estimateId", createEstimateId)
                .put("api/estimates/{estimateId}")
                .then()
                .assertThat()
                .statusCode(STATUS_UNAUTHORIZED)
                .body(Matchers.equalTo("Login first"));
    }

    /**
     * Test case QAA-6137.
     */
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "QAA-6137:Создание оценки в API EM без тела запроса")
    public void negativeCreateEstimateNoBody() {
        given()
                .header("Authorization", getToken())
                .post("api/estimates")
                .then().assertThat()
                .statusCode(STATUS_SERVER_ERROR)
                .contentType(ContentType.HTML);
    }
}
