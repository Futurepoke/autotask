import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FirstTest {

    private WebDriver driver = new ChromeDriver();

    JavascriptExecutor js = (JavascriptExecutor) driver;

    private final Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);

    private String url = "https://yandex.ru/";

    @BeforeClass
    public void startBrowser() {

        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get(url);
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
        try {
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        } catch (IOException e) {
        }
    }

    private boolean isElementPresent(By by) {
        try {
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    public void listCountTest() {

        driver.findElement(By.xpath("//a[@data-id = 'market']")).click();

        driver.findElement(By.xpath("//ul[@class='topmenu__list']//a[@class='link topmenu__link' and contains(text(), 'Компьютеры')]")).click();

        driver.findElement(By.xpath("//div[@class='catalog-menu__list']//a[contains(text(), 'Ноутбуки')]")).click();

        driver.findElement(By.xpath("//div[@id='search-prepack']//input[@id='glpriceto']")).sendKeys("30000");

        WebElement element = driver.findElement(By.xpath("//div[@class='_3TQHHekI1h']//input[@name='Производитель Lenovo']"));
        Actions act = new Actions(driver);
        act.moveToElement(element)
                .click()
                .build()
                .perform();

        element = driver.findElement(By.xpath("//div[@class='_3TQHHekI1h']//input[@name='Производитель HP']"));
        act.moveToElement(element)
                .click()
                .build()
                .perform();

        int count = driver.findElements(By.xpath("//div[@class='n-snippet-card2__title']")).size();

        assertThat("Count of elements: ", count, is(48));

    }

    @Test
    public void listNamesTest() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='preloadable__preloader preloadable__preloader_visibility_visible preloadable__paranja']")));

        int count = driver.findElements(By.xpath("//div[@class='n-snippet-card2__title']/a[contains(text(), 'Lenovo') or contains(text(), 'HP')]")).size();
        assertThat("HPs and Lenovos: ", count, is(48));

    }

}
