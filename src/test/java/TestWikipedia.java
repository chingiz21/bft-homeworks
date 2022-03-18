import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWikipedia {

    @Test
    public void search() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en-us");

        WebDriver driver=new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.wikipedia.org/");
        WebElement username = driver.findElement(By.id("searchInput"));
        WebElement button = driver.findElement(By.className("pure-button"));

        username.sendKeys("USA");
        button.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String expectedArticle ="United States";
        WebElement articleHeading = driver.findElement(By.xpath("/html/body//h1"));
        String actualArticle = articleHeading.getText();
        assertEquals(expectedArticle, actualArticle);

        String expectedUrl="https://en.wikipedia.org/wiki/United_States";
        String actualUrl= driver.getCurrentUrl();
        assertEquals(expectedUrl,actualUrl);

        driver.quit();
    }

    @Test
    public void login() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en-us");

        WebDriver driver=new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://en.wikipedia.org/w/index.php?title=Special:UserLogin");

        WebElement username = driver.findElement(By.id("wpName1"));
        WebElement password = driver.findElement(By.id("wpPassword1"));
        WebElement loginButton = driver.findElement(By.id("wpLoginAttempt"));

        username.sendKeys("userTestbftbfu");
        password.sendKeys("123456789!");
        loginButton.click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String expectedUser = "UserTestbftbfu";

        WebElement currentUser = driver.findElement(By.xpath("/html/body/div/div/nav/div/ul/li[1]/a/span"));
        String currentUserText = currentUser.getText();
        assertEquals(expectedUser, currentUserText);

        String expectedUrl="https://en.wikipedia.org/wiki/Main_Page";
        String actualUrl= driver.getCurrentUrl();
        assertEquals(expectedUrl,actualUrl);

        driver.quit();

    }

    @Test
    public void logout() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en-us");

        WebDriver driver=new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://en.wikipedia.org/w/index.php?title=Special:UserLogin");

        WebElement username = driver.findElement(By.id("wpName1"));
        WebElement password = driver.findElement(By.id("wpPassword1"));
        WebElement loginButton = driver.findElement(By.id("wpLoginAttempt"));

        username.sendKeys("userTestbftbfu");

        password.sendKeys("123456789!");
        loginButton.click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement logoutButton = driver.findElement(By.id("pt-logout"));
        logoutButton.click();

        WebElement userStatus = driver.findElement(By.xpath("html/body/div/div/nav/div/ul/li[1]/span"));
        String userStatusText = userStatus.getText();
        String expectedUserStatus = "Not logged in";
        assertEquals(expectedUserStatus, userStatusText);

        String expectedUrl="https://en.wikipedia.org/w/index.php?title=Special:UserLogout&returnto=Main+Page";
        String actualUrl= driver.getCurrentUrl();
        assertEquals(expectedUrl,actualUrl);

        driver.quit();

    }
}
