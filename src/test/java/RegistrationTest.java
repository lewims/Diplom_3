import client.User;
import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page_object.LoginPage;
import page_object.MainPage;
import page_object.RegisterPage;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.RandomStringUtils.*;

@RunWith(Parameterized.class)
public class RegistrationTest {

    private WebDriver driver;
    private String driverType;
    public static String accessToken;

    static String name = randomAlphanumeric(4, 8);
    static String email = randomAlphanumeric(6, 10) + "@yandex.ru";
    static String password = randomAlphanumeric(10, 20);
    String wrongPassword = randomAlphanumeric(1, 5);
    static User user = new User(name, email, password);


    public RegistrationTest(String driverType) {
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
    @DisplayName("Успешная регистрация.")
    @Description("Проверка успешной регистрации.")
    public void successfulRegistrationTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnRegister();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.waitForLoadRegisterPage();
        registerPage.registration(name, email, password);
        loginPage.waitForLoadEntrance();
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя.")
    @Description("Проверяем неуспешную регистрацию пользователя при вводе пароля меньше 6 символов, и появление сообщения 'Некорректный пароль'.")
    public void failedPasswordRegistrationTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnRegister();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.waitForLoadRegisterPage();
        registerPage.registration(name, email, wrongPassword);
        //Проверка появление текста "Некорректный пароль"
        Assert.assertTrue("Некорректный пароль", driver.findElement(registerPage.errorPasswordText).isDisplayed());
    }

    @After
    public void tearDown() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        accessToken = UserClient.checkRequestAuthLogin(user).then().extract().path("accessToken");
        if (accessToken != null){
        UserClient.deleteUser(accessToken);
        }
        driver.quit();
    }


}
