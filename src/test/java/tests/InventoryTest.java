package tests;

import base.BaseTest;
import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;

public class InventoryTest extends BaseTest {
    private JsonObject testData;

    @BeforeClass
    public void loadData() {
        testData = DataDriven.jsonReader("testData/testData.json");
    }

    @Test
    public void testInventoryPageElements() {
        Assert.assertNotNull(testData, "Test data file could not be loaded!");
        JsonObject validUser = testData.getAsJsonObject("validUser");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(validUser.get("username").getAsString(), validUser.get("password").getAsString());

        InventoryPage inventoryPage = new InventoryPage(driver);

        // Assertions
        Assert.assertEquals(inventoryPage.getPageTitle(), "Swag Labs");
        Assert.assertTrue(inventoryPage.isCartIconDisplayed(), "Cart icon is not visible on the inventory page.");
        Assert.assertEquals(inventoryPage.getProductCount(), 6);
    }
}
