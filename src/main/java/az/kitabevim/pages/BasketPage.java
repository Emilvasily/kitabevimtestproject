package az.kitabevim.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasketPage extends ExceptionHandler { //To reuse exceptions handling methods

    private final WebDriver driver;
    private final WebDriverWait wait;

    public BasketPage (WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    //Objects on the Basket Page

    @FindBy(xpath = "//h1[contains(text(), 'Səbətim')]")
    private WebElement basketPageTitle;

    @FindBy(xpath = "(//input[contains(@value, '+')])[3]")
    private WebElement increaseItemCountBtn;

    @FindBy(xpath = "//button[contains(@value, 'Səbəti yenilə')]")
    private WebElement updateBasketBtn;

    @FindBy(xpath = "//div[contains(text(), 'Səbət yeniləndi')]")
    private WebElement basketUpdated;

    @FindBy(xpath = "(//a[contains(@class, 'remove')])[3]")
    private WebElement removeItemBtn;

    @FindBy(xpath = "//p[contains(text(), 'Səbətiniz hazırda boşdur')]")
    private WebElement emptyBasketText;

    //Methods of the Basket Page

    public String getBasketPageTitle(){
        return getElementsText(wait, basketPageTitle);
    }

    public void increaseItemCount(){
        clickOnElement(wait, increaseItemCountBtn);
    }

    public void updateBasket(){
        clickOnElement(wait, updateBasketBtn);
    }

    public boolean isBasketUpdated(){
        wait.until(ExpectedConditions.visibilityOf(basketUpdated));
        return basketUpdated.isDisplayed();
    }

    public void removeItem(){
        clickOnElement(wait, removeItemBtn);
    }

    public String getEmptyBasketText(){
        return getElementsText(wait, emptyBasketText);
    }
}
