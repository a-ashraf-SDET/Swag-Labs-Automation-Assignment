package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class CartPage {
    private WebDriver driver;

    private By cartItems = By.className("cart_item");
    private By cartItemNames = By.className("inventory_item_name");
    private By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getCartItemsCount() {
        List<WebElement> items = driver.findElements(cartItems);
        return items.size();
    }

    public List<String> getCartItemNames() {
        List<WebElement> elements = driver.findElements(cartItemNames);
        List<String> names = new ArrayList<>();
        for (WebElement element : elements) {
            names.add(element.getText());
        }
        return names;
    }

    public void removeProductFromCart(String productName) {
        String removeXpath = "//div[@class='inventory_item_name' and text()='" + productName + "']/ancestor::div[@class='cart_item_label']//button";
        driver.findElement(By.xpath(removeXpath)).click();
    }


    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }
}
