package tests.api;

import io.qameta.allure.Epic;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

@Epic("Страница авторизации")
public class LoginPageTest extends BaseApi {

    /**
     * Test QAA-48.
     */
    @Test(description = "QAA-48:Авторизация по api")
    public void authorizationTest() {
        authorization(JSON_AUTH_ADMIN)
                .then()
                .body("token", Matchers.notNullValue(),
                        "expire", Matchers.notNullValue(),
                        "user", Matchers.notNullValue(),
                        "role", Matchers.notNullValue(),
                        "role._id", Matchers.notNullValue(),
                        "role.name", Matchers.notNullValue(),
                        "role.permissions", Matchers.notNullValue(),
                        "role.permissions.all.all", Matchers.notNullValue(),
                        "role.permissions.all.all", Matchers
                                .hasItems("list", "view", "update", "create",
                                "delete", "export", "status"),
                        "role.canExportMoney", Matchers.equalTo(true));
    }
}
