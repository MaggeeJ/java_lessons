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
        if (app.db().contacts().isEmpty()) {
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withMiddlename("Vasil").withLastname("Makarov").withNickname("Pups")
                    .withCompany("Groovy").withAddress("Kazahstan, Gandolyerov 98").withMobilePhone("+79881112233")
                    .withEmail("makarov@gmail.com").withBday("1").withBmonth("January").withByear("2014"));
        }
    }

    @Test(enabled = true)
    public void testContactModification() {
        app.goTo().homePage();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("Ivan").withMiddlename("Ivanovich").withLastname("Ivanov")
                .withNickname("Petr").withCompany("Petrovka").withAddress("Petrograd, Petrogradnaya 206").withMobilePhone("+79889996677")
                .withEmail("tt@gmail.com").withBday("6").withBmonth("May").withByear("1996");
        app.contact().modify(contact);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }

}
