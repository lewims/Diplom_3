package WebDriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverSwitch {
    static WebDriver driver ;

    public static WebDriver getWebDriver(String browserName) {

        if (browserName.equals("chromedriver")) {
            System.setProperty("webdriver.chrome.driver", "../chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);

        } else if (browserName.equals("yandexdriver")) {
            System.setProperty("webdriver.chrome.driver", "../yandexdriver.exe");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        }
        return driver;
    }
}
