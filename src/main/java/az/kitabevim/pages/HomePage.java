package az.kitabevim.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends ExceptionHandler { //To reuse exceptions handling methods

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage (WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    //Objects on the Home Page

    @FindBy(xpath = "(//input[@placeholder='Məhsullarda axtarış'])[2]")
    //There are 2 similar elements, another is used for mobile version
    private WebElement searchBar;

    @FindBy (xpath = "//span[contains(text(), 'Bütün hüquqları qorunur')]")
    private WebElement copyrightInfo;

    //Methods of the Home Page

    public String getCopyrightInfo(){
        //Just to verify that page is fully available
        return getElementsText(wait, copyrightInfo);
    }

    public void findABookByName(String bookName){
        searchBar.sendKeys(bookName);
        searchBar.sendKeys(Keys.ENTER);
    }

}
