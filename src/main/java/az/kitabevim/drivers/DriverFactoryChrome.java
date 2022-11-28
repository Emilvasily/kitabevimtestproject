package az.kitabevim.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactoryChrome {

    public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<WebDriver>();

    public static synchronized void initializeDriver(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
                "--disable-dev-shm-usage"
        );
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driverThreadLocal.set(new ChromeDriver());
    }

    public static synchronized WebDriver getDriver(){
        return driverThreadLocal.get();
    }

}