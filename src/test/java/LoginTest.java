import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.After;
import org.openqa.selenium.chrome.ChromeOptions;
import page_object.LoginPage;
import page_object.MainPage;
import page_object.RecoverPasswordPage;
import page_object.RegisterPage;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginTest {
    private WebDriver driver;
    private String driverType;
    String name = randomAlphanumeric(4, 8);
    String email = randomAlphanumeric(6, 10) + "@yandex.ru";;
    String password = randomAlphanumeric(6, 10);
    public static String accessToken;
    public LoginTest(String driverType) {
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
            driver.navigate().to("https://stellarburgers.nomoreparties.site/");
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
    @DisplayName("Вход по кнопке 'Войти в аккаунт'.")
    @Description("Проверка кнопки 'Войти в аккаунт' на главной странице лендинга.")
    public void enterByLoginButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.authorization(email, password);
        mainPage.waitForLoadMainPage();
        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход по кнопке 'Личный Кабинет'.")
    @Description("Проверка кнопки 'Личный Кабинет' на хедере главной страницы.")
    public void enterByPersonalAccountButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnAccountButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.authorization(email, password);
        mainPage.waitForLoadMainPage();
        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации.")
    @Description("Проверка входа через форму регистрации.")
    public void enterByRegistrationFormTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnRegister();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickOnLoginButtonOnRegistrationPage();
        loginPage.waitForLoadEntrance();
        loginPage.authorization(email, password);
        mainPage.waitForLoadMainPage();
        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля.")
    @Description("Проверка входа через форму восстановления пароля.")
    public void enterByPasswordRecoveryFormatTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnAccountButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickOnForgotPasswordLink();
        RecoverPasswordPage recoverPasswordPage = new RecoverPasswordPage(driver);
        recoverPasswordPage.waitForLoadedRecoverPassword();
        recoverPasswordPage.clickOnLoginLink();
        loginPage.authorization(email, password);
        mainPage.waitForLoadMainPage();
        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @After
    public void tearDown() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        accessToken = UserClient.checkRequestAuthLogin(RegistrationTest.user).then().extract().path("accessToken");
        if (accessToken != null){
            UserClient.deleteUser(accessToken);
        }
        driver.quit();
    }
}

