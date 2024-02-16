package az.kitabevim.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Actions {

    private final WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(Actions.class);

    public Actions(WebDriverWait wait) {
        this.wait = wait;
    }

    public void clickOnElement(WebElement element) {
        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.info("Clicked {}", element.getAccessibleName());
        } catch (StaleElementReferenceException | TimeoutException | NoSuchElementException exception) {
            logger.error("FAIL: Can't click: " + element.getAccessibleName() + " " + exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    public String getElementsText(WebElement element) {
        try {
            element = wait.until(ExpectedConditions.visibilityOf(element));
            logger.info("Visible {}", element.getAccessibleName());
            return element.getText();
        } catch (StaleElementReferenceException | TimeoutException | NoSuchElementException exception) {
            logger.error("FAIL: Can't locate: " + element.getAccessibleName() + " " + exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

}
