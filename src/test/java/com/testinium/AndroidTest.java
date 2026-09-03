package com.testinium;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

public class AndroidTest extends BaseTest {

    @Test
    public void basicAndroidTest() throws InterruptedException {
        Thread.sleep(20000);

        WebElement generalButton = driver.findElement(AppiumBy.xpath("//*[contains(@text, \"Kategoriler\")]"));
        generalButton.click();
        logger.info("Clicked to Kategoriler");

        Thread.sleep(2000);
    }

    @Test
    @Disabled("Geçici olarak kapalı / refactor bekliyor")
    public void skippedTest() throws InterruptedException {
        WebElement generalButton = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"com.gratis.android:id/navigation_bar_item_icon_view\"])[1]"));
        generalButton.click();
        logger.info("Clicked to HomePage");

        Thread.sleep(2000);

        WebElement kategorilerButton = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.gratis.android:id/navigation_bar_item_small_label_view\" and @text=\"Kategoriler\"]"));
        kategorilerButton.click();
        logger.info("Clicked to Kategoriler Button");
    }

    @Test
    public void basicAndroidFailTest() throws InterruptedException {
        WebElement generalButton = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@resource-id=\"com.gratis.android:id/navigation_bar_item_icon_view_error\"])[1]"));
        generalButton.click();
        logger.info("Clicked to HomePage");

        Thread.sleep(2000);

        WebElement kategorilerButton = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.gratis.android:id/navigation_bar_item_small_label_view_error\" and @text=\"Kategoriler\"]"));
        kategorilerButton.click();
        logger.info("Clicked to Kategoriler Button");

        Thread.sleep(2000);

        WebElement makyajButton = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.gratis.android:id/category_name_error\" and @text=\"Makyaj\"]"));
        makyajButton.click();
        logger.info("Clicked to Makyaj Button");

        Thread.sleep(2000);

        WebElement backButton = driver.findElement(AppiumBy.id("com.gratis.android:id/btnBack"));
        backButton.click();
        logger.info("Clicked to Back Button");

        Thread.sleep(2000);
    }
}
