package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().list().isEmpty()) {
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withMiddlename("Vasil").withLastname("Makarov").withNickname("Pups")
                    .withCompany("Groovy").withAddress("Kazahstan, Gandolyerov 98").withMobile("+79881112233")
                    .withEmail("makarov@gmail.com").withBday("1").withBmonth("January").withByear("2014").withGroup("test1"));
        }
    }

    @Test(enabled = true)
    public void testContactDeletion() {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        app.contact().delete(index);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        Assert.assertEquals(before, after);

    }


}
