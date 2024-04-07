package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    private static WebDriver driver;

    private static final String EMAIL = "gordeich70@mail.ru";
    private static final String PASSWORD = "Alesh@-Popov1ch";

    @BeforeAll
    public static void setUpDriver() {
        String driverPath = new File("./.driver/chromedriver.exe").getAbsolutePath();
        System.setProperty("webdriver.chrome.driver", driverPath);

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void dropDriver() {
        driver.quit();
    }

    @Test
    void habrTest() {
        driver.get("https://habr.com/ru/all");
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div[2]/a[1]")).click();

        WebElement searchRow = driver.findElement(By.xpath("//input[@name='q']"));

        assertEquals(searchRow, driver.switchTo().activeElement());

        searchRow.sendKeys("Selenium WebDriver");
        driver.findElement(By.xpath("//*[@class='tm-svg-img tm-svg-icon']")).click();

        driver.findElement(By.linkText("Что такое Selenium?")).click();

        String date = driver.findElement(By.xpath("//time[@datetime='2012-09-28T09:14:11.000Z']")).getText();
        assertTrue(date.contains("28 сен 2012"));

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        driver.findElement(By.xpath("//*/a[@href='/ru/articles/' and @class='footer-menu__item-link router-link-active']")).click();
    }

    @Test
    void mailRuTest() {
        driver.get("https://mail.ru");

        driver.findElement(By.xpath("/html/body/main/div[2]/div[2]/div[1]/div/div[1]/button")).click();

        WebElement frame = driver.findElement(By.xpath("/html/body/div[5]/div/iframe"));
        WebElement usernameField = driver.switchTo().frame(frame).findElement(By.xpath("//input[@name='username']"));
        usernameField.clear();
        usernameField.sendKeys(EMAIL);

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.clear();
        passwordField.sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div[2]/span")).click();

        String name = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/div/div[2]/div/div/div[1]/div/div[1]/div")).getText();
        assertTrue(name.contains("Гордей Гордеич"));

        driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/div/div[2]/div/div/div[3]/div[2]")).click();

        By createMailButton = By.linkText("Создать почту");

        assertTrue(driver.findElement(createMailButton).isDisplayed());
    }
}
