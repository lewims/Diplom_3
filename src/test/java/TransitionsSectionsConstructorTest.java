import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page_object.MainPage;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class TransitionsSectionsConstructorTest {

    private WebDriver driver;
    private String driverType;

    public TransitionsSectionsConstructorTest(String driverType) {
        this.driverType = driverType;
    }

    @Before
    public void startUp() {
        if (driverType.equals("chromedriver")) {
            System.setProperty("webdriver.chrome.driver", "C:/Users/lewims/IdeaProjects/Diplom__3/src/main/resources/chromedriver");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.navigate().to("https://stellarburgers.nomoreparties.site/");
        } else if (driverType.equals("yandexdriver")) {
            System.setProperty("webdriver.chrome.driver", "C:/Users/lewims/IdeaProjects/Diplom__3/src/main/resources/yandexdriver");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.navigate().to("https://stellarburgers.nomoreparties.site/");
        }
    }

    @Parameterized.Parameters(name = "Результаты проверок браузера: {0}")
    public static Object[][] getDataDriver() {
        return new Object[][]{
                {"chromedriver"},
                {"yandexdriver"},
        };
    }

    @Test
    @DisplayName("Переход в раздел 'Булки'.")
    @Description("Проверка перехода в раздел 'Булки', и появление картинки с булкой.")
    public void transitionToBunsInConstructorTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnSaucesButton();
        mainPage.clickOnBunsButton();
        mainPage.checkToppingBun();
    }

    @Test
    @DisplayName("Переход в раздел 'Соусы'.")
    @Description("Проверка перехода в раздел 'Соусы', и появление картинки с соусом.")
    public void transitionToSaucesInConstructorTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnSaucesButton();
        mainPage.checkToppingSauce();
    }

    @Test
    @DisplayName("Переход в раздел 'Начинки'.")
    @Description("Проверка перехода в раздел 'Начинки', и появление картинки с начинкой.")
    public void transitionToFillingsInConstructorTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnFillingButton();
        mainPage.checkToppingFillings();
    }

    @After
    public void tearDown() {
        // Закрытие браузера
        driver.quit();
    }

}
