package az.kitabevim.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class ExceptionHandler {

    public void clickOnElement(WebDriverWait webDriverWait, WebElement webElement) {
        //Attempts to find element
        for (int i = 0; i < 2; i++) {
            try {
                webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
                webElement.click();
                break;
            } catch (StaleElementReferenceException | TimeoutException | NoSuchElementException exception) {
                System.out.println("Another try because of: " + webElement + "\n" + Arrays.toString(exception.getStackTrace()));
            }
        }
        System.out.println("Failed to locate element: " + webElement);
    }

    public String getElementsText(WebDriverWait webDriverWait, WebElement webElement) {
        //Attempts to find element
        for (int i = 0; i < 2; i++) {
            try {
                webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
                return webElement.getText();
            } catch (StaleElementReferenceException | TimeoutException | NoSuchElementException exception) {
                System.out.println("Another try because of: " + webElement + "\n" + Arrays.toString(exception.getStackTrace()));
            }
        }
        return "Failed to locate element";
    }
}
