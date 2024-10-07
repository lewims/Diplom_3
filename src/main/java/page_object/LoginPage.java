package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;

    public final By entrance = By.xpath(".//main/div/h2[text()='Вход']");
    private final By emailField = By.xpath(".//div[@class='input pr-6 pl-6 input_type_text input_size_default']/input[@name='name']");
    private final By passwordField = By.xpath(".//div[@class='input pr-6 pl-6 input_type_password input_size_default']/input[@name='Пароль']");
    public final By enterButton = By.xpath(".//div/a[@href='/']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By constructorButton = By.xpath(".//a/p[text()='Конструктор']");
    private final By registerLink = By.xpath(".//a[@href='/register' and text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath(".//a[@href='/forgot-password' and text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ввод Email")
    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик по кнопке 'Войти'.")
    public void clickOnLoginButton() {
        driver.findElement(loginButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Клик по ссылке 'Зарегистрироваться'.")
    public void clickOnRegister() {
        driver.findElement(registerLink).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Клик по ссылке 'Восстановить пароль'.")
    public void clickOnForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Клик по кнопке 'Конструктор'")
    public void clickOnConstructorButton() {
        driver.findElement(constructorButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Клик по кнопке 'Stellar Burgers'.")
    public void clickOnLogo() {
        driver.findElement(enterButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Авторизация пользователя.")
    public void authorization(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickOnLoginButton();
    }

    @Step("Выставлено ожидание загрузки страницы с текстом 'Вход'.")
    public void waitForLoadEntrance() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(entrance));
    }

    @Step("Выставлено ожидание загрузки страницы полностью, анимация исчезнет.")
    public void waitForInvisibilityLoadingAnimation() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
    }
}
