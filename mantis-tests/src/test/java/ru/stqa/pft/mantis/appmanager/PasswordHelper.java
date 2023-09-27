package ru.stqa.pft.mantis.appmanager;
import org.openqa.selenium.By;

public class PasswordHelper extends HelperBase {

    public PasswordHelper(ApplicationManager app) {
        super(app);
    }

    public void loginAsAdmin() {
        wd.get(app.getProperty("web.baseURL") + "login_page.php");
        type(By.name("username"),"administrator");
        click(By.xpath("//input[@value='Login']"));
        type(By.name("password"), "root");
        click(By.xpath("//input[@value='Login']"));
    }

    public void goToManage(){
        click(By.xpath("//*[@id=\"sidebar\"]/ul/li[7]/a/span"));
    }

    public void goToManageUser(){
        click(By.xpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/ul/li[2]/a"));
    }

    public void selectUser(){
        click(By.xpath("//tr[2]/td/a"));
    }

    public void resetPassword(){
        click(By.cssSelector("input[value='Reset Password']"));
    }

    public void  goToUserPage(){
        goToManage();
        goToManageUser();
        selectUser();
    }

    public String getUserName(){
        return wd.findElement(By.name("username")).getAttribute("value");
    }

    public void changePassword(String confirmationToLink, String password) {
        wd.get(confirmationToLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("span.bigger-110"));
    }
}
