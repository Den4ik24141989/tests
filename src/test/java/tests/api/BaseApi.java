package tests.api;

import com.google.gson.Gson;
import helpers.ParametersProvider;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.CreatePhaseDTO;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BaseApi {

    /**
     * Token authorization.
     */
    private String token;

    /**
     * Status code 200.
     */
    protected static final int STATUS_OK = 200;

    /**
     * Status code 401.
     */
    protected static final int STATUS_UNAUTHORIZED = 401;

    /**
     * Status code 500.
     */
    protected static final int STATUS_SERVER_ERROR = 500;

    /**
     * Phase id from mobile app directory.
     */
    protected static final String PHASE_MOBILE_APP = "57871931437d35620b630b38";

    /**
     * Json file for authorization.
     */
    protected static final File JSON_AUTH_ADMIN =
            new File("src/test/resources/authAdmin.json");

    /**
     * Json file for create estimate.
     */
    protected static final File JSON_CREATE_ESTIMATE =
            new File("src/test/resources/createEstimate.json");

    /**
     * Json file for edit estimate.
     */
    protected static final File JSON_EDIT_ESTIMATE =
            new File("src/test/resources/editEstimate.json");

    /**
     * Json schema for validation estimate.
     */
    protected static final File JSON_SCHEMA_ESTIMATE =
            new File("src/test/resources/schemaEstimate.json");

    /**
     * Json schema for validation estimate.
     */
    protected static final File JSON_SCHEMA_CREATE_ESTIMATE =
            new File("src/test/resources/schemaCreateEstimate.json");

    /**
     * Prepare RestAssured.
     * @throws IOException when config file is not available
     */
    @BeforeClass
    public void prepare() throws IOException {
        String baseUri = ParametersProvider.getProperty("webUrl");
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setAccept(ContentType.JSON)
                .setContentType("application/json;charset=utf-8")
                .log(LogDetail.ALL)
                .build();

        token = "Bearer " + authorization(JSON_AUTH_ADMIN)
                .body().jsonPath().get("token");
    }

    /**
     * Authorization method.
     * @param json json object authorization
     * @return response
     */
    public Response authorization(final File json) {
        return given().body(json)
                .when()
                .post("api/auth/login")
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();
    }

    /**
     * Create estimate.
     * @return requested response
     */
    public Response createEstimate() {
        return given()
                .body(JSON_CREATE_ESTIMATE)
                .header("Authorization", getToken())
                .post("api/estimates")
                .then().statusCode(STATUS_OK)
                .extract().response();
    }

    /**
     * Get estimate.
     * @param estimateId id requested estimate
     * @return requested response
     */
    public Response getEstimate(final String estimateId) {
        return given().header("Authorization", token)
                .pathParam("estimateId", estimateId)
                .get("api/estimates/{estimateId}")
                .then()
                .extract().response();
    }

    /**
     * Create estimate and extract id.
     * @return estimate id
     */
    public String createEstimateId() {
        return createEstimate()
                .then()
                .extract()
                .body()
                .jsonPath()
                .get("_id");
    }

    /**
     * Add phase in estimate.
     * @param phaseDTO object phaseDTO
     * @param estimateId estimate id
     * @return phase
     */
    public Response addPhase(final CreatePhaseDTO phaseDTO,
                             final String estimateId) {
        return given().body(new Gson().toJson(phaseDTO))
                .header("Authorization", getToken())
                .pathParam("estimateId", estimateId)
                .post("api/estimates/{estimateId}/phases")
                .then().extract().response();
    }

    /**
     * Add phase in estimate.
     * @param estimateId estimate id
     * @param phaseId phase id
     * @return response
     */
    public Response deletePhase(final String estimateId, final String phaseId) {
        return given().header("Authorization", getToken())
                .pathParam("estimateId", estimateId)
                .pathParam("phase", phaseId)
                .delete("/api/estimates/{estimateId}/phases/{phase}")
                .then()
                .extract().response();
    }

    /**
     * Get token authorization.
     * @return token authorization
     */
    public String getToken() {
        return token;
    }
}
