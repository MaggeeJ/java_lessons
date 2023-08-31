package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactdata, boolean creation) {
        if (creation) {
            if (! isTextPresent(By.name("new_group"), contactdata.getGroup())) {
                click(By.linkText("groups"));
                click(By.name("new"));
                type("group_name", contactdata.getGroup());
                click(By.name("submit"));
                click(By.linkText("add new"));
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactdata.getGroup());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
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



    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void closeAlert() {
        wd.switchTo().alert().accept();
    }

    public void initContactModification(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }

    public void returnToHomePage() {
        click(By.cssSelector("#nav > ul:nth-child(1) > li:nth-child(1) > a:nth-child(1)"));
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
        returnToHomePage();

    }

    public void modify(int index, List<ContactData> before, ContactData contact) {
        selectContact(index);
        initContactModification(before.get(index).getId());
        fillContactForm(contact, false);
        submitContactModification();
        returnToHomePage();
    }

    public void delete(int index) {
        selectContact(index);
        deleteSelectedContacts();
        closeAlert();
        returnToHomePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(String.valueOf(Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"))));
            String lastName = cells.get(1).getText();
            String firstName = cells.get(2).getText();
            contacts.add(new ContactData().withId(id).withFirstname(firstName).withLastname(lastName));
        }
        return contacts;
    }
}
