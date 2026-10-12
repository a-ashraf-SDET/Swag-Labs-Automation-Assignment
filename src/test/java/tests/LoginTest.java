package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DataDriven;
import com.google.gson.JsonObject;


public class LoginTest extends BaseTest {
    private JsonObject testData;

    @BeforeClass
    public void loadData() {
        testData = DataDriven.jsonReader("testData/testData.json");
    }


    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        JsonObject validUser = testData.getAsJsonObject("validUser");

        // استخدام getAsString لمنع ظهور علامات التنصيص الزائدة
        loginPage.login(validUser.get("username").getAsString(), validUser.get("password").getAsString());

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
    }

    @Test
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        JsonObject invalidUser = testData.getAsJsonObject("invalidUser");

        loginPage.login(invalidUser.get("username").getAsString(), invalidUser.get("password").getAsString());

        String error = loginPage.getErrorMessageText();
        Assert.assertTrue(error.contains("Username and password do not match"));
    }

    @Test
    public void testLoginWithoutPassword() {
        LoginPage loginPage = new LoginPage(driver);
        JsonObject validUser = testData.getAsJsonObject("validUser");

        loginPage.enterUsername(validUser.get("username").getAsString());
        loginPage.clickLogin();

        String error = loginPage.getErrorMessageText();
        Assert.assertTrue(error.contains("Password is required"));
    }

}
