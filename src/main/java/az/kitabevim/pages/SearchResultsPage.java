package az.kitabevim.pages;

import az.kitabevim.logs.LoggerFile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class SearchResultsPage extends ExceptionHandler { //To reuse exceptions handling methods

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchResultsPage (WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    //Objects on the Results Page

    @FindBy(xpath = "//h1[contains(text(), 'Axtarışın nəticələri')]")
    private WebElement resultsPageTitle;

//    @FindBy(xpath = "//section[contains(@class, 'type-product')]")
//    private List<WebElement> booksList;
    @FindBy(xpath = "//div[contains(@class, 'thumbnail-wrapper')]")
    private List<WebElement> booksList;

    //Methods of the Results Page

    public String getResultsPageTitle(){
        return getElementsText(wait, resultsPageTitle);
    }

    public void getRandomBook(){
        Random randomBook = new Random();
        if(booksList.size() != 0){
            int book = randomBook.nextInt(booksList.size());
            wait.until(ExpectedConditions.visibilityOf(booksList.get(book)));
            clickOnElement(wait, booksList.get(book)); //get(1) to check non-available book
        } else {
            LoggerFile.warn("0 books were found");
            //System.out.println("0 books were found");
        }
    }
}
