package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final  By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final  By itemTotalLabel = By.className("summary_subtotal_label");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillInformation(String first, String last, String zip) {
        driver.findElement(firstName).sendKeys(first);
        driver.findElement(lastName).sendKeys(last);
        driver.findElement(postalCode).sendKeys(zip);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    public double getItemTotal() {
        String totalText = driver.findElement(itemTotalLabel).getText();
        return Double.parseDouble(totalText.replaceAll("[^0-9.]", ""));
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }
}
