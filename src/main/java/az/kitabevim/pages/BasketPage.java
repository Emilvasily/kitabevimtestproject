package az.kitabevim.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasketPage extends Actions {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public BasketPage (WebDriver driver, WebDriverWait wait){
        super(wait);
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
        return getElementsText(basketPageTitle);
    }

    public void increaseItemCount(){
        clickOnElement(increaseItemCountBtn);
    }

    public void updateBasket(){
        clickOnElement(updateBasketBtn);
    }

    public boolean isBasketUpdated(){
        return basketUpdated.isDisplayed();
    }

    public void removeItem(){
        clickOnElement(removeItemBtn);
    }

    public String getEmptyBasketText(){
        return getElementsText(emptyBasketText);
    }

}
