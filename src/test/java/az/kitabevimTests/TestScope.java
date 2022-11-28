package az.kitabevimTests;

import az.kitabevim.csvReader.CSVProcessing;
import az.kitabevim.drivers.DriverFactoryChrome;
import az.kitabevim.logs.LoggerFile;
import az.kitabevim.pages.BasketPage;
import az.kitabevim.pages.BookPage;
import az.kitabevim.pages.HomePage;
import az.kitabevim.pages.SearchResultsPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Epic("KitabEvim Test Task")
@Feature("Add/Delete selected book")
public class TestScope {

    private final CSVProcessing csvProcessing = new CSVProcessing();
    private String keyword;

    @BeforeTest
    public void parsKeyword(){
        LoggerFile.info("Parsing CSV");
        keyword = csvProcessing.getSearchValue();
    }

    @BeforeMethod
    public void setupChromeDriver(){
        DriverFactoryChrome.initializeDriver();
    }

    @Test
    public void addBookToBasketThenDeleteIt(){

        LoggerFile.info("Initializing things");
        WebDriver driver = DriverFactoryChrome.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        HomePage homePage = new HomePage(driver, wait);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver, wait);
        BookPage bookPage = new BookPage(driver, wait);
        BasketPage basketPage = new BasketPage(driver, wait);

        LoggerFile.info("Navigate to https://www.kitabevim.az and execute test. " +
                "Any additional information on execution will be added on methods implementation level");
        driver.get("https://www.kitabevim.az");
        driver.manage().window().maximize();
        //Confirm page title
        Assert.assertTrue(driver.getTitle().contains("Kitabevim.az e-mağaza"));
        //Confirm footer availability
        Assert.assertTrue(homePage.getCopyrightInfo().contains("Copyright © 2020"));
        //Type keyword parsed from CSV
        homePage.findABookByName(keyword);
        //Confirm that results page is available
        Assert.assertTrue(searchResultsPage.getResultsPageTitle().contains(keyword));
        searchResultsPage.getRandomBook();
        //Confirm that book page is available by checking status
        Assert.assertTrue(bookPage.getBookAvailabilityText().contains("ANBARDA"));
        //Confirm that book is available or skip test
        if(!bookPage.addBookToBasket()) {
            LoggerFile.warn("Book is not available. Skipping test");
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
        LoggerFile.info("Test is finished. You can find result-artifacts in Allure folder");
    }

    @AfterMethod
    public void quitDriver(){
        WebDriver driver = DriverFactoryChrome.getDriver();
        driver.quit();
        LoggerFile.info("Closing driver");
    }

}
