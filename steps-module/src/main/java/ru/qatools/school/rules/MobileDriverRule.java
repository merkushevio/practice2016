package ru.qatools.school.rules;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by merkushevio on 19.05.2016.
 */
public class MobileDriverRule extends ExternalResource {

    private WebDriver driver;

    protected void before() throws Throwable {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", "Android");
        desiredCapabilities.setCapability("app", "http://autoschool.github.io/files/ya-metro.apk");
        desiredCapabilities.setCapability("appWaitActivity", "ru.yandex.metro.MainActivity");
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

//    protected void after() {
//        driver.close();
//        driver.quit();
//    }

    public WebDriver getDriver() {
        return driver;
    }
}
