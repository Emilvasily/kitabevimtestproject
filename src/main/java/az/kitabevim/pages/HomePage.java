package az.kitabevim.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends Actions {

    private static final Logger logger = LogManager.getLogger(Actions.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage (WebDriver driver, WebDriverWait wait){
        super(wait);
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    //Objects on the Home Page

    @FindBy(xpath = "(//input[@placeholder='Məhsullarda axtarış'])[2]")
    private WebElement searchBar;

    @FindBy (xpath = "//span[contains(text(), 'Bütün hüquqları qorunur')]")
    private WebElement copyrightInfo;

    //Methods of the Home Page

    public String getCopyrightInfo(){
        return getElementsText(copyrightInfo);
    }

    public void findABookByName(String bookName){
        searchBar.sendKeys(bookName);
        searchBar.sendKeys(Keys.ENTER);
    }

}
