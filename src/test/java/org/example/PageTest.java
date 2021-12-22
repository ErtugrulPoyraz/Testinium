package org.example;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public abstract class PageTest {
    public WebDriver driver;
    private static Logger log = Logger.getLogger(PageTest.class.getName());

    @Before
    public void beforeCreateWebDriver(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        System.setProperty("webdriver.chrome.driver","C:\\Users\\ASUS\\Desktop\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60,TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);

        PropertyConfigurator.configure("path_to_log4j.properties");

    }

    @After
    public void afterQuitWebDriver(){
    driver.quit();
    }
}
