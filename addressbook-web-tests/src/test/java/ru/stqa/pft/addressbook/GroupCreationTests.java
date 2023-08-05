package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GroupCreationTests {
    private WebDriver wb;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        wb = new FirefoxDriver();
        wb.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wb.get("http://localhost/addressbook/");
        login("admin", "secret");
    }

    @Test
    public void testGroupCreation() throws Exception {
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("test4", "test5", "test6"));
        submitGroupCreation();
        gotoGroupPage();
        logout();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        wb.quit();
    }

    private void login(String username, String password) {
        wb.findElement(By.name("user")).clear();
        wb.findElement(By.name("user")).sendKeys(username);
        wb.findElement(By.name("pass")).clear();
        wb.findElement(By.name("pass")).sendKeys(password);
        wb.findElement(By.xpath("//input[@value='Login']")).click();
    }
    private void logout() {
        wb.findElement(By.linkText("Logout")).click();
    }

    private void submitGroupCreation() {
        wb.findElement(By.name("submit")).click();
    }

    private void fillGroupForm(GroupData groupData) {
        wb.findElement(By.name("group_name")).click();
        wb.findElement(By.name("group_name")).clear();
        wb.findElement(By.name("group_name")).sendKeys(groupData.getName());
        wb.findElement(By.name("group_header")).click();
        wb.findElement(By.name("group_header")).clear();
        wb.findElement(By.name("group_header")).sendKeys(groupData.getHeader());
        wb.findElement(By.name("group_footer")).click();
        wb.findElement(By.name("group_footer")).clear();
        wb.findElement(By.name("group_footer")).sendKeys(groupData.getFooter());
    }

    private void initGroupCreation() {
        wb.findElement(By.name("new")).click();
    }

    private void gotoGroupPage() {
        wb.findElement(By.linkText("groups")).click();
    }

}
