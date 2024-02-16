package az.kitabevim.Tests;

import az.kitabevim.drivers.LocalChrome;
import az.kitabevim.pages.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("KitabEvim Test Task")
@Feature("Add/Delete selected book")
public class TestScope {

    private static final Logger logger = LogManager.getLogger(Actions.class);
    private final WebDriver driver = LocalChrome.getDriver();
    private String keyword;

    @BeforeTest
    public void parsKeyword(){
        logger.info("Assigning keyword");
        keyword = "Book name";
    }

    @BeforeMethod
    public void setupChromeDriver(){
        LocalChrome.initializeDriver();
    }

    @Test
    public void addBookToBasketThenDeleteIt(){
        logger.info("Initializing things");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        HomePage homePage = new HomePage(driver, wait);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver, wait);

        BookPage bookPage = new BookPage(driver, wait);
        BasketPage basketPage = new BasketPage(driver, wait);

        logger.info("Navigate to https://www.kitabevim.az and execute test. " +
                "Any additional information on execution will be added on methods implementation level");
        driver.get("https://www.kitabevim.az");
        driver.manage().window().maximize();
        //Confirm page title
        Assert.assertTrue(driver.getTitle().contains("Kitabevim.az e-mağaza"));
        //Confirm footer availability
        Assert.assertTrue(homePage.getCopyrightInfo().contains("Copyright ©"));
        //Type keyword parsed from CSV
        homePage.findABookByName(keyword);
        //Confirm that results page is available
        Assert.assertTrue(searchResultsPage.getResultsPageTitle().contains(keyword));
        searchResultsPage.getRandomBook();
        //Confirm that book page is available by checking status
        Assert.assertTrue(bookPage.getBookAvailabilityText().contains("ANBARDA"));
        //Confirm that book is available or skip test
        if(!bookPage.addBookToBasket()) {
            logger.warn("Book is not available. Skipping test");
            throw new SkipException("Book is not available. Skipping test");
        }
        //Confirm that item count is increased by 1
        Assert.assertTrue(bookPage.itemsCountChecked());
        //Hover on basket then click goTo
        bookPage.hoverOnBasket();
        bookPage.goToBasket();
        //Confirm that basket page is available then increase item count
        Assert.assertTrue(basketPage.getBasketPageTitle().contains("Səbətim"));
        basketPage.increaseItemCount();
        basketPage.updateBasket();
        //Confirm that basket is updated then remove it
        Assert.assertTrue(basketPage.isBasketUpdated());
        basketPage.removeItem();
        //Confirm that basket is empty
        Assert.assertTrue(basketPage.getEmptyBasketText().contains("Səbətiniz hazırda boşdur"));
        logger.info("Test is finished. You can find result-artifacts in Allure folder");
    }

    @AfterMethod
    public void quitDriver(){
        driver.quit();
        logger.info("Closing browser");
    }

}
