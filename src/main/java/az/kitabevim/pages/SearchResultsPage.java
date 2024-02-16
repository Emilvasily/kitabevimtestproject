package az.kitabevim.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class SearchResultsPage extends Actions {

    private static final Logger logger = LogManager.getLogger(Actions.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchResultsPage (WebDriver driver, WebDriverWait wait){
        super(wait);
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    //Objects on the Results Page

    @FindBy(xpath = "//h1[contains(text(), 'Axtarışın nəticələri')]")
    private WebElement resultsPageTitle;

    @FindBy(xpath = "//div[contains(@class, 'thumbnail-wrapper')]")
    private List<WebElement> booksList;

    //Methods of the Results Page

    public String getResultsPageTitle(){
        return getElementsText(resultsPageTitle);
    }

    public void getRandomBook(){
        Random randomBook = new Random();
        if(booksList.size() != 0){
            int book = randomBook.nextInt(booksList.size());
            wait.until(ExpectedConditions.visibilityOf(booksList.get(book)));
            clickOnElement(booksList.get(book));
        } else {
            logger.warn("0 books were found");
        }
    }

}
