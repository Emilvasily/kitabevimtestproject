package az.kitabevimTests;

import az.kitabevim.csvReader.CSVProcessing;
import az.kitabevim.drivers.DriverFactoryChrome;
import az.kitabevim.pages.BasketPage;
import az.kitabevim.pages.BookPage;
import az.kitabevim.pages.HomePage;
import az.kitabevim.pages.SearchResultsPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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
    private static Logger log = Logger.getLogger(TestScope.class);

    @BeforeTest
    public void parsKeyword(){
        keyword = csvProcessing.getSearchValue();
    }

    @BeforeMethod
    public void setupChromeDriver(){
        DriverFactoryChrome.initializeDriver();
    }

    @Test
    public void addBookToBasketThenDeleteIt(){
        PropertyConfigurator.configure("src/main/java/az/kitabevim/logs/log4j.properties");
        WebDriver driver = DriverFactoryChrome.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        HomePage homePage = new HomePage(driver, wait);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver, wait);
        BookPage bookPage = new BookPage(driver, wait);
        BasketPage basketPage = new BasketPage(driver, wait);

        driver.get("https://www.kitabevim.az");
        log.info("go to URL");
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
        if(!bookPage.addBookToBasket()) throw new SkipException("Book is not available");
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
        log.info("Finish");
    }

    @AfterMethod
    public void quitDriver(){
        WebDriver driver = DriverFactoryChrome.getDriver();
        driver.quit();
    }

}
