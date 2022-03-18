import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestWikipedia2 {
    public static WebDriver driver;

    @BeforeAll
    public static void before(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en-us");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void after(){
        driver.quit();
    }

    @Step("Open {url}")
    public void openPage(String url){
        driver.get(url);
    }

    @Step("Find element by id")
    public WebElement findByID(String elementID) {
        return driver.findElement(By.id(elementID));
    }

    @Step("Find element by className")
    public WebElement findByClassName(String elementClassName) {
        return driver.findElement(By.className(elementClassName));
    }

    @Step("Send text {text} to {element}")
    public void sendKeys(WebElement element, String text) {
        element.sendKeys(text);
    }

    @Step("Click {element}")
    public void click(WebElement element){
        element.click();
    }

    @Step("Check article {articleName}")
    public void checkArticle(String articleName){
        WebElement articleHeading = driver.findElement(By.xpath("/html/body//h1"));
        String actualArticle = articleHeading.getText();
        assertEquals(articleName, actualArticle);
    }

    @Step("Check user {login}")
    public void checkUser(String userName) {

        WebElement currentUser = driver.findElement(By.xpath("/html/body/div/div/nav/div/ul/li[1]/a/span"));
        String currentUserText = currentUser.getText();
        assertEquals(userName, currentUserText);
    }

    @Step("Check url {url}")
    public void checkURL(String url){
        String actualUrl= driver.getCurrentUrl();
        assertEquals(url,actualUrl);
    }

    @Step("Get loggined {login}, {password}")
    public void getLogged(String login, String password) {
        openPage("https://en.wikipedia.org/w/index.php?title=Special:UserLogin");

        sendKeys(findByID("wpName1"), login);
        sendKeys(findByID("wpPassword1"), password);
        click(findByID("wpLoginAttempt"));
    }

    @Step("Check logout {logoutTitle}")
    public void checkLogout(String logoutTitle) {
        click(findByID("pt-logout"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement elementTitle = driver.findElement(By.xpath("/html/body/div/div/nav/div/ul/li[1]/span"));
        String currentTitle = elementTitle.getText();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        assertEquals(logoutTitle, currentTitle);
    }

    @Test
    @DisplayName("Search")
    public void checkArticle() {
        openPage("https://www.wikipedia.org/");

        sendKeys(findByID("searchInput"), "USA");
        click(findByClassName("pure-button"));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        checkArticle("United States");
        checkURL("https://en.wikipedia.org/wiki/United_States");
    }

    @Test
    @DisplayName("Login")
    public void login() {
        openPage("https://en.wikipedia.org/w/index.php?title=Special:UserLogin");

        sendKeys(findByID("wpName1"), "userTestbftbfu");
        sendKeys(findByID("wpPassword1"), "123456789!");
        click(findByID("wpLoginAttempt"));

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        checkUser("UserTestbftbfu");
        checkURL("https://en.wikipedia.org/wiki/Main_Page");
    }

    @Test
    @DisplayName("Logout")
    public void logout() {
        getLogged("userTestbftbfu", "123456789!");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        checkURL("https://en.wikipedia.org/wiki/Main_Page");
        checkLogout("Not logged in");
    }
}
