package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

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
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("Иван").withMiddlename("Иванович").withLastname("Иванов")
                .withNickname("Петр").withCompany("Петруздель").withAddress("Петрозаводск, Петроградня 206").withMobile("+79889996677")
                .withEmail("tt@gmail.com").withBday("6").withBmonth("May").withByear("1996");
        app.contact().modify(contact);
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }

}
