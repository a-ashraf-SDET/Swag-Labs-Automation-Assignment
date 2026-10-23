package tests;

import base.BaseTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;
import java.util.List;
import java.util.Set;

public class CartTest extends BaseTest {
    private JsonObject testData;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginAndInitialize() {
        testData = DataDriven.jsonReader("testData/testData.json");
        JsonObject validUser = testData.getAsJsonObject("validUser");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(validUser.get("username").getAsString(), validUser.get("password").getAsString());

        inventoryPage = new InventoryPage(driver);
    }

    @Test(priority = 1)
    public void testSocialLinks() {
        String originalWindow = driver.getWindowHandle();

        utils.WaitUtils.waitForElementToBeVisible(driver, By.className("footer"), 5);

        inventoryPage.clickLinkedin();
        switchToNewWindow(originalWindow);
        Assert.assertTrue(driver.getCurrentUrl().contains("linkedin"), "URL does not contain linkedin");
        driver.close();
        driver.switchTo().window(originalWindow);

        inventoryPage.clickFacebook();
        switchToNewWindow(originalWindow);
        Assert.assertTrue(driver.getCurrentUrl().contains("facebook"), "URL does not contain facebook");
        driver.close();
        driver.switchTo().window(originalWindow);

        inventoryPage.clickTwitter();
        switchToNewWindow(originalWindow);
        Assert.assertTrue(driver.getCurrentUrl().contains("x.com") || driver.getCurrentUrl().contains("twitter"), "URL does not contain ://x.com");
        driver.close();
        driver.switchTo().window(originalWindow);
    }


    @Test(priority = 2)
    public void testVerifyCartIsEmpty() {
        inventoryPage.clickCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemsCount(), 0, "Cart is not empty!");
    }

    @Test(priority = 3)
    public void testAddSpecificProducts() {
        JsonArray productsArray = testData.getAsJsonArray("cartProducts");

        String item1 = productsArray.get(0).getAsString();
        String item2 = productsArray.get(1).getAsString();
        String item3 = productsArray.get(2).getAsString();

        inventoryPage.addProductToCart(item1);
        inventoryPage.addProductToCart(item2);
        inventoryPage.addProductToCart(item3);

        inventoryPage.clickCart();
        CartPage cartPage = new CartPage(driver);

        Assert.assertEquals(cartPage.getCartItemsCount(), 3);
        List<String> namesInCart = cartPage.getCartItemNames();

        Assert.assertEquals(namesInCart.get(0), item1);
        Assert.assertEquals(namesInCart.get(1), item2);
        Assert.assertEquals(namesInCart.get(2), item3);
    }

    @Test(priority = 4)
    public void testRemoveOneProduct() {
        JsonArray productsArray = testData.getAsJsonArray("cartProducts");
        String item1 = productsArray.get(0).getAsString();
        String item2 = productsArray.get(1).getAsString();
        String item3 = productsArray.get(2).getAsString();

        inventoryPage.addProductToCart(item1);
        inventoryPage.addProductToCart(item2);
        inventoryPage.addProductToCart(item3);

        inventoryPage.clickCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.removeProductFromCart(item2);

        Assert.assertEquals(cartPage.getCartItemsCount(), 2);

        driver.navigate().back();

        Assert.assertEquals(inventoryPage.getProductButtonText(item1), "Remove");
        Assert.assertEquals(inventoryPage.getProductButtonText(item2), "Add to cart");
        Assert.assertEquals(inventoryPage.getProductButtonText(item3), "Remove");
    }



    @Test(priority = 5)
    public void testVerifyCartTotalPrice() {
        JsonArray productsArray = testData.getAsJsonArray("cartProducts");
        String item1 = productsArray.get(0).getAsString();
        String item2 = productsArray.get(1).getAsString();
        String item3 = productsArray.get(2).getAsString();

        double price1 = inventoryPage.getProductPrice(item1);
        double price2 = inventoryPage.getProductPrice(item2);
        double price3 = inventoryPage.getProductPrice(item3);
        double expectedTotal = price1 + price2 + price3;

        inventoryPage.addProductToCart(item1);
        inventoryPage.addProductToCart(item2);
        inventoryPage.addProductToCart(item3);

        inventoryPage.clickCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInformation("TestFirst", "TestLast", "12345");
        checkoutPage.clickContinue();

        double actualTotal = checkoutPage.getItemTotal();
        Assert.assertEquals(actualTotal, expectedTotal, "Calculated price summary mismatch!");
    }


    @Test(priority = 6)
    public void testCheckoutWithAnEmptyCart() {
        inventoryPage.clickCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),
                "The application did not navigate to the checkout details page!");
    }

    @Test(priority = 7)
    public void testCartStateAfterLogoutLogin() {
        JsonArray productsArray = testData.getAsJsonArray("cartProducts");
        inventoryPage.addProductToCart(productsArray.get(0).getAsString());
        inventoryPage.addProductToCart(productsArray.get(1).getAsString());

        driver.findElement(By.id("react-burger-menu-btn")).click();
        try { Thread.sleep(1000); } catch (Exception e) {} // Small pause for sidebar animation stability
        driver.findElement(By.id("logout_sidebar_link")).click();

        JsonObject validUser = testData.getAsJsonObject("validUser");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(validUser.get("username").getAsString(), validUser.get("password").getAsString());

        inventoryPage.clickCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemsCount(), 2, "Cart state was cleared unexpectedly upon session re-entry!");
    }

    private void switchToNewWindow(String originalWindow) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}
