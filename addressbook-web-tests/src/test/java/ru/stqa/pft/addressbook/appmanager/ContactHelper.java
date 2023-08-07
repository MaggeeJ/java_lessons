package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactdata, boolean creation) {
        type("firstname", contactdata.getFirstname());
        type("middlename", contactdata.getMiddlename());
        type("lastname", contactdata.getLastname());
        type("nickname", contactdata.getNickname());
        type("company", contactdata.getCompany());
        type("address", contactdata.getAddress());
        type("mobile", contactdata.getMobile());
        type("email", contactdata.getEmail());
        typeList("bday", contactdata.getBday());
        typeList("bmonth", contactdata.getBmonth());
        type("byear", contactdata.getByear());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactdata.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContact() {
        click(By.name("selected[]"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void closeAlert() {
        wd.switchTo().alert().accept();
    }

    public void initContactModification() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void createContact(ContactData contact, boolean b) {
        initContactCreation();
        fillContactForm(contact, b);
        submitContactCreation();
        returnToHomePage();

    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }
}
