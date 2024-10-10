package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.*;

public class MainPage {

    private final WebDriver driver;

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By accountButton = By.xpath(".//a/p[text()='Личный Кабинет']");
    private final By logo = By.xpath(".//div/a[@href='/']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By bunsButton = By.xpath("//span[@class='text text_type_main-default'][text()='Булки']");
    private final By saucesButton = By.xpath("//span[@class='text text_type_main-default'][text()='Соусы']");
    private final By fillingsButton = By.xpath("//span[@class='text text_type_main-default'][text()='Начинки']");
    private final By activityTopping = By.xpath("//div[starts-with(@class,'tab_tab__1SPyG tab_tab_type_current__2BEPc')]//span");
    private final By basketButton = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket__container')]/button");
    private final By headerLinks = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");

    public By bunsImg = By.xpath(".//img[@alt='Краторная булка N-200i']");
    public By bunsText = By.xpath(".//h2[text()='Булки']");
    public By saucesImg = By.xpath(".//p[text()='Соус с шипами Антарианского плоскоходца']");
    public By fillingsImg = By.xpath(".//img[@alt='Плоды Фалленианского дерева']");
    public By textBurgerMainPage = By.xpath(".//section/h1[text()='Соберите бургер']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public void clickOnLoginButton() {
        driver.findElement(loginButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Клик по кнопке 'Личный Кабинет'")
    public void clickOnAccountButton() {
        driver.findElement(accountButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Клик по логотипу 'Stellar Burgers'")
    public void clickOnLogo() {
        driver.findElement(logo).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Клик по кнопке 'Конструктор'")
    public void clickOnConstructorButton() {
        driver.findElement(constructorButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Клик по кнопке 'Булки'")
    public void clickOnBunsButton() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(bunsButton).click();
    }

    @Step("Клик по кнопке 'Соуса'")
    public void clickOnSaucesButton() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(saucesButton).click();
    }

    @Step("Клик по кнопке 'Начинки'")
    public void clickOnFillingButton() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(fillingsButton).click();
    }

    public void checkToppingBun() throws InterruptedException {
        Thread.sleep(500);
        String countActivity = driver.findElement(activityTopping).getText();
        assertEquals("Булки", countActivity);
    }

    public void checkToppingSauce() throws InterruptedException {
        Thread.sleep(500);
        String countActivity = driver.findElement(activityTopping).getText();
        assertEquals(countActivity,"Соусы");
    }

    public void checkToppingFillings() throws InterruptedException {
        Thread.sleep(1000);
        String countActivity = driver.findElement(activityTopping).getText();
        assertEquals(countActivity,"Начинки");
    }

    @Step("Выставлено ожидание главная страница, и загрузка текста 'Соберите бургер'")
    public void waitForLoadMainPage() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(textBurgerMainPage));
    }

    @Step("Выставлено ожидание загрузки текст и картинка с булкой на главной странице")
    public void waitForLoadBunsHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(bunsImg));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(bunsText));
    }

    @Step("Выставлено ожидание загрузки картинка с соусом на главной странице.")
    public void waitForLoadSaucesHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(saucesImg));
        waitDocReady();

    }

    @Step("Выставлено ожидание загрузки с начинкой на главной странице.")
    public void waitForLoadFillingsHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(fillingsImg));
        waitDocReady();
    }

    @Step("Проверка состояния кнопки корзины")
    public String getBasketButtonText() {
        return driver.findElement(basketButton).getText();
    }
    public void clickLinkToProfile() {
        waitForInvisibilityLoadingAnimation();
        driver.findElements(headerLinks).get(2).click();
    }

    @Step("Выставлено ожидание загрузки страницы полностью, анимация исчезнет.")
    public void waitForInvisibilityLoadingAnimation() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
        waitDocReady();
    }

    @Step("Выставлено ожидание загрузки страницы полностью, дополнительный метод ожидания.")
    public void waitDocReady() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until((ExpectedCondition<Boolean>) wd ->
                        ((JavascriptExecutor) wd)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }
}
