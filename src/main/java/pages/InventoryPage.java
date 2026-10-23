package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class InventoryPage {
    private final WebDriver driver;

    private final By cartIcon = By.className("shopping_cart_link");
    private final By inventoryItems = By.className("inventory_item");
    private final By twitterLink = By.xpath("//a[contains(@href, 'twitter.com') or contains(@href, 'x.com')]");
    private final By facebookLink = By.xpath("//a[contains(@href, 'facebook.com')]");
    private final By linkedinLink = By.xpath("//a[contains(@href, 'linkedin.com')]");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isCartIconDisplayed() {
        return driver.findElement(cartIcon).isDisplayed();
    }

    public int getProductCount() {
        List<WebElement> items = driver.findElements(inventoryItems);
        return items.size();
    }

    public void openProductDetails(String productName) {
        driver.findElement(By.xpath("//div[text()='" + productName + "']")).click();
    }

    public void clickTwitter() {
        driver.findElement(twitterLink).click();
    }

    public void clickFacebook() {
        driver.findElement(facebookLink).click();
    }

    public void clickLinkedin() {
        driver.findElement(linkedinLink).click();
    }


    public void addProductToCart(String productName) {
        String buttonXpath = "//*[contains(text(), '" + productName + "')]/ancestor::div[@class='inventory_item_description']//button";
        driver.findElement(By.xpath(buttonXpath)).click();
    }

    public double getProductPrice(String productName) {
        String priceXpath = "//*[contains(text(), '" + productName + "')]/ancestor::div[@class='inventory_item_description']//div[@class='inventory_item_price']";
        String priceText = driver.findElement(By.xpath(priceXpath)).getText();
        return Double.parseDouble(priceText.replace("$", ""));
    }

    public String getProductButtonText(String productName) {
        String buttonXpath = "//*[contains(text(), '" + productName + "')]/ancestor::div[@class='inventory_item_description']//button";
        return driver.findElement(By.xpath(buttonXpath)).getText();
    }


    public void clickCart() {
        driver.findElement(cartIcon).click();
    }
}
