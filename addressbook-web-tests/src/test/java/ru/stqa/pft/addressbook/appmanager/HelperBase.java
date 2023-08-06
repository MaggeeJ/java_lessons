package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class HelperBase {
    public WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    public void click(By locator) {
        wd.findElement(locator).click();
    }

    public void type(String locator, String text) {
        click(By.name(locator));
        wd.findElement(By.name(locator)).clear();
        wd.findElement(By.name(locator)).sendKeys(text);
    }

    public void typeList(String locator, String text) {
        wd.findElement(By.name(locator)).click();
        new Select(wd.findElement(By.name(locator))).selectByVisibleText(text);
        wd.findElement(By.xpath("//option[@value='" + text + "']")).click();
    }

    public boolean isAlertPresent () {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
