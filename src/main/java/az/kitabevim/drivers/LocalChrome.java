package az.kitabevim.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LocalChrome {

    public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void initializeDriver(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("src/main/resources/chrome-win64/chrome.exe");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driverThreadLocal.set(new ChromeDriver());
    }

    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    }

}
