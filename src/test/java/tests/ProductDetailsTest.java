package tests;

import base.BaseTest;
import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductDetailsPage;
import utils.DataDriven;

public class ProductDetailsTest extends BaseTest {
    private JsonObject testData;

    @BeforeClass
    public void loadData() {
        testData = DataDriven.jsonReader("testData/testData.json");
    }

    @Test
    public void testProductDetailsAndAddToCart() {
        JsonObject validUser = testData.getAsJsonObject("validUser");
        JsonObject productDetails = testData.getAsJsonObject("productDetails");
        String targetProductName = productDetails.get("name").getAsString();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(validUser.get("username").getAsString(), validUser.get("password").getAsString());

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.openProductDetails(targetProductName);

        ProductDetailsPage detailsPage = new ProductDetailsPage(driver);

        Assert.assertEquals(detailsPage.getProductName(), targetProductName);
        Assert.assertEquals(detailsPage.getProductPrice(), productDetails.get("expectedPrice").getAsString());
        // Extract the raw source string and verify it contains our expected file name directly
        String actualImageSrc = detailsPage.getProductImageSrc();
        String expectedImageName = productDetails.get("expectedImage").getAsString();

        Assert.assertTrue(actualImageSrc.toLowerCase().contains(expectedImageName.toLowerCase()),
                "Product image mismatch! Expected to find: " + expectedImageName + " but got: " + actualImageSrc);

        detailsPage.clickAddToCart();
        Assert.assertEquals(detailsPage.getCartBadgeCount(), "1");
    }
}
