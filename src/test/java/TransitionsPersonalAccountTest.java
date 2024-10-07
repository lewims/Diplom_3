import client.User;
import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page_object.LoginPage;
import page_object.MainPage;
import page_object.ProfilePage;
import page_object.RegisterPage;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@RunWith(Parameterized.class)
public class TransitionsPersonalAccountTest {

    private WebDriver driver;
    private String driverType; //добавила в код
    static String name = randomAlphanumeric(4, 8);
    static String email = randomAlphanumeric(6, 10) + "@yandex.ru";
    static String password = randomAlphanumeric(10, 20);
    static User user = new User(name, email, password);
    public static String accessToken;
    public TransitionsPersonalAccountTest(String driverType) {
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
            MainPage mainPage = new MainPage(driver);
            mainPage.clickOnLoginButton();
            LoginPage loginPage = new LoginPage(driver);
            loginPage.clickOnRegister();
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.waitForLoadRegisterPage();
            registerPage.registration(name, email, password);
            loginPage.waitForLoadEntrance();
            loginPage.authorization(email, password);
        } else if (driverType.equals("yandexdriver")) {
            System.setProperty("webdriver.chrome.driver", "C:/Users/lewims/IdeaProjects/Diplom__3/src/main/resources/yandexdriver");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.navigate().to("https://stellarburgers.nomoreparties.site/");
            MainPage mainPage = new MainPage(driver);
            mainPage.clickOnLoginButton();
            LoginPage loginPage = new LoginPage(driver);
            loginPage.clickOnRegister();
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.waitForLoadRegisterPage();
            registerPage.registration(name, email, password);
            loginPage.waitForLoadEntrance();
            loginPage.authorization(email, password);
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
    @DisplayName("Переход в личный кабинет.")
    @Description("Проверка перехода по клику на 'Личный кабинет'.")
    public void transitionToProfilePageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnAccountButton();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForLoadProfilePage();
        Assert.assertTrue("Страница профиля не отобразилась", driver.findElement(profilePage.textOnProfilePage).isDisplayed());
    }

    @Test
    @DisplayName("Переход в конструктор из личного кабинета.")
    @Description("Проверка перехода на вкладку 'Конструктор' из страницы авторизации пользователя.")
    public void transitionToConstructorFromProfilePageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitForInvisibilityLoadingAnimation();
        mainPage.clickOnAccountButton();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForLoadProfilePage();
        profilePage.clickOnConstructorButton();
        mainPage.waitForLoadMainPage();
        Assert.assertTrue("Переход  в конструктор из личного кабинете не прошел", driver.findElement(mainPage.textBurgerMainPage).isDisplayed());
    }

    @Test
    @DisplayName("Клик по логотипу 'Stellar Burgers'.")
    @Description("Проверка перехода в конструктор при нажатии на логотип 'Stellar Burgers'.")
    public void transitionToStellarBurgersFromProfilePageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnAccountButton();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForLoadProfilePage();
        mainPage.clickOnLogo();
        mainPage.waitForLoadMainPage();
        Assert.assertTrue("Конструктор при клике на логотип не загрузился", driver.findElement(mainPage.textBurgerMainPage).isDisplayed());
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Проверка выхода по кнопке 'Выйти' в личном кабинете.")
    public void exitFromProfileTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnAccountButton();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForLoadProfilePage();
        profilePage.clickOnExitButton();
        mainPage.waitForInvisibilityLoadingAnimation();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue("Не удалось выйти из аккаунта", driver.findElement(loginPage.entrance).isDisplayed());
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