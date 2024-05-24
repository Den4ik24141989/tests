package tests.api;

import io.qameta.allure.Epic;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("Управление ролями пользователей")
public class ChangingUserRoleTest extends BaseApi {

    /**
     * Test QAA-57.
     * Estimator to moderator
     */
    @Test(description = "QAA-57:Изменение роли пользователя по API")
    public void changingUserRoleEstimatorToModerator() {
        given().header("Authorization", getToken())
                .put("api/users/662a5d8c157f9c0051311d49"
                        + "/role/5786029528a9c2b643a0d9c0")
                .then()
                .body("_id", Matchers.equalTo("662a5d8c157f9c0051311d49"),
                        "cn", Matchers.equalTo("estimator"),
                        "uid", Matchers.equalTo("estimator"),
                        "role._id", Matchers
                                .equalTo("5786029528a9c2b643a0d9c0"),
                        "role.name", Matchers.equalTo("moderator"),
                        "role.permissions.currency.all",
                        Matchers.hasItems("create", "delete"),
                        "role.permissions.role.all",
                        Matchers.hasItems("list", "view", "create",
                                "update", "delete"),
                        "role.permissions.workhour.all",
                        Matchers.hasItems("list", "view", "create",
                                "update", "delete"),
                        "role.permissions.user.ownUser",
                        Matchers.hasItems("list", "view", "update"),
                        "role.permissions.task.all",
                        Matchers.hasItems(
                                "list", "view", "create", "update", "delete"),
                        "role.permissions.tag.all",
                        Matchers.hasItems(
                                "list", "view", "create", "update", "delete"),
                        "role.permissions.statistic.all",
                        Matchers.hasItems(
                                "list", "view", "create", "update", "delete"),
                        "role.permissions.phase.all",
                        Matchers.hasItems("list", "view", "create",
                                "update", "delete"),
                        "role.permissions.group.all",
                        Matchers.hasItems("list", "view", "create",
                                "update", "delete"),
                        "role.permissions.feature.all",
                        Matchers.hasItems("list", "view", "create",
                                "update", "delete"),
                        "role.permissions.estimate.all",
                        Matchers.hasItems("list", "view", "create", "update",
                                "status", "export", "delete"),
                        "role.canExportMoney", Matchers.equalTo(true));
    }

    /**
     * Test QAA-57.
     * Estimator to estimator
     */
    @Test(description = "QAA-57:Изменение роли пользователя по API")
    public void changingUserRoleEstimatorToEstimator() {
        given().header("Authorization", getToken())
                .put("api/users/662a5d8c157f9c0051311d49/"
                        + "role/57866530b6c29d370695a908")
                .then()
                .body("_id", Matchers.equalTo("662a5d8c157f9c0051311d49"),
                        "cn", Matchers.equalTo("estimator"),
                        "uid", Matchers.equalTo("estimator"),
                        "role._id", Matchers
                                .equalTo("57866530b6c29d370695a908"),
                        "role.name", Matchers.equalTo("estimator"),
                        "role.permissions.all.ownEstimate",
                        Matchers.hasItems(
                                "create", "list", "view", "update", "status"),
                        "role.permissions.estimate.managers",
                        Matchers.hasItems("list", "view"),
                        "role.permissions.estimate.approvers",
                        Matchers.hasItems("list", "view"),
                        "role.permissions.estimate.ownEstimate",
                        Matchers.hasItems(
                                "create", "list", "view", "update", "status"),
                        "role.permissions.estimate.all",
                        Matchers.hasItems("create"),
                        "role.permissions.feature.all",
                        Matchers.hasItems("create"),
                        "role.permissions.feature.ownEstimate",
                        Matchers.hasItems("delete"),
                        "role.permissions.group.all",
                        Matchers.hasItems("list", "view"),
                        "role.permissions.phase.all",
                        Matchers.hasItems("create", "list", "view"),
                        "role.permissions.phase.ownEstimate",
                        Matchers.hasItems("delete"),
                        "role.permissions.statistic.all",
                        Matchers.hasItems("create"),
                        "role.permissions.tag.all",
                        Matchers.hasItems("list", "view"),
                        "role.permissions.task.all",
                        Matchers.hasItems("create"),
                        "role.permissions.task.ownEstimate",
                        Matchers.hasItems("delete"),
                        "role.permissions.user.ownUser",
                        Matchers.hasItems("list", "view", "update"),
                        "role.permissions.workhour.all",
                        Matchers.hasItems("list", "view"),
                        "role.canExportMoney", Matchers.equalTo(false));
    }
}
