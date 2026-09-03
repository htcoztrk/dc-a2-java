package com.testinium;

import com.testinium.util.Constants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.testinium.driver.TestiniumAndroidDriver;
import com.testinium.driver.TestiniumIOSDriver;
import com.testinium.util.TestiniumEnvironment;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static com.testinium.util.Constants.CapabilityConstants.*;
import static com.testinium.util.Constants.CapabilityConstants.APPIUM_AUTO_ACCEPT_ALERTS;
import static com.testinium.util.Constants.PLATFORM_NAME;

public abstract class BaseTest {

    protected static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected static AppiumDriver driver;
    protected static URL hubUrl;
    protected static FluentWait<AppiumDriver> appiumFluentWait;
    static Boolean DeviceAndroid = false;

    @BeforeAll
    public static void setup() {
        try {
            hubUrl = new URL("https://dev-devicepark-appium-gw-service.testinium.io/wd/hub");

            if (DeviceAndroid || TestiniumEnvironment.isPlatformAndroid()) {
                DesiredCapabilities overridden = new DesiredCapabilities();
                overridden.setCapability(PLATFORM_NAME, Platform.ANDROID);
                overridden.setCapability(UDID, "YOUR_UDID");
                overridden.setCapability(APPIUM_AUTOMATION_NAME, "YOUR_AUTOMATION_NAME");
                overridden.setCapability(APPIUM_APP_PACKAGE, "YOUR_APP_PACKAGE");
                overridden.setCapability(APPIUM_APP_ACTIVITY, "YOUR_APP_ACTIVITY");
                overridden.setCapability(APPIUM_AUTO_GRANT_PERMISSIONS, true);
                overridden.setCapability(APPIUM_NEW_COMMAND_TIMEOUT, 60000);
                driver = new TestiniumAndroidDriver(hubUrl, overridden);

                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                appiumFluentWait = new FluentWait<AppiumDriver>(driver);

            } else {
                DesiredCapabilities overridden = new DesiredCapabilities();
                overridden.setCapability(Constants.PLATFORM_NAME, Platform.IOS);
                overridden.setCapability(UDID, "YOUR_UDID");
                overridden.setCapability(APPIUM_AUTOMATION_NAME, "YOUR_AUTOMATION_NAME");
                overridden.setCapability(APPIUM_BUNDLE_ID, "YOUR_BUNDLE_ID");
                overridden.setCapability(APPIUM_AUTO_ACCEPT_ALERTS, true);
                driver = new TestiniumIOSDriver(hubUrl, overridden);

                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                appiumFluentWait = new FluentWait<AppiumDriver>(driver);
            }
            appiumFluentWait.withTimeout(Duration.ofSeconds(8))
                    .pollingEvery(Duration.ofMillis(350))
                    .ignoring(NoSuchElementException.class);

        } catch (MalformedURLException e) {
            logger.error("MalformedURLException: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Setup Error", e);
        }
    }

    @AfterAll
    public static void teardown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            logger.error("Teardown Error: " + e.getMessage());
        }
    }
}
