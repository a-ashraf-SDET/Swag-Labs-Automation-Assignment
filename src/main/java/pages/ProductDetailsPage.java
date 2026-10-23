package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage {
    private WebDriver driver;

    private final By productName = By.className("large_size");
    private final By productPrice = By.className("inventory_details_price");
    private final By addToCartButton = By.id("add-to-cart");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By productImage = By.className("inventory_details_img");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public String getCartBadgeCount() {
        return driver.findElement(cartBadge).getText();
    }

    public String getProductImageSrc() {
        return driver.findElement(productImage).getAttribute("src");
    }
}
