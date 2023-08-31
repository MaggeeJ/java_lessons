package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().isEmpty()) {
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withMiddlename("Vasil").withLastname("Makarov").withNickname("Pups")
                    .withCompany("Groovy").withAddress("Kazahstan, Gandolyerov 98").withMobile("+79881112233")
                    .withEmail("makarov@gmail.com").withBday("1").withBmonth("January").withByear("2014").withGroup("test1"));
        }
    }

    @Test(enabled = true)
    public void testContactDeletion() {
        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedContact);
        Assert.assertEquals(before, after);

    }


}
