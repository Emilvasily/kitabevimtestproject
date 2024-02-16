package az.kitabevim.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookPage extends Actions {

    private static final Logger logger = LogManager.getLogger(Actions.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    public BookPage (WebDriver driver, WebDriverWait wait){
        super(wait);
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    //Objects on the Book Page

    @FindBy(xpath = "//p[contains(@class, 'availability stock')]")
    private WebElement bookAvailabilityText;

    @FindBy(xpath = "//button[contains(text(), 'Səbətə at')]")
    private WebElement addBookToBasketBtn;

    @FindBy(xpath = "(//span[contains(@class, 'cart-number')])[2]")
    private WebElement itemsCount;

    @FindBy(xpath = "(//a[contains(@title, 'Səbətə baxın')])[2]")
    private WebElement basketIcon;

    @FindBy(xpath = "(//a[contains(@class, 'button view-cart')])[2]")
    private WebElement goToBasketBtn;

    //Methods of the Book Page

    public String getBookAvailabilityText(){
        return getElementsText(bookAvailabilityText);
    }

    public boolean addBookToBasket(){
        if (bookAvailabilityText.getText().equals("ANBARDA")){
            clickOnElement(addBookToBasketBtn);
            return true;
        } else {
            logger.warn("No books are available");
            return false;
        }
    }

    public boolean itemsCountChecked(){
        return wait.until(ExpectedConditions.textToBePresentInElement(itemsCount, "1"));
    }

    public void hoverOnBasket(){
        basketIcon.sendKeys(Keys.HOME);
        org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(driver);
        action.moveToElement(basketIcon).perform();
    }

    public void goToBasket(){
        clickOnElement(goToBasketBtn);
    }

}
