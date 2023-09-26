package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

public class ContactsInGroupsTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().isEmpty()) {
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("Anton").withMiddlename("Vasil").withLastname("Makarov").withNickname("Pups")
                    .withCompany("Groovy").withAddress("Kazahstan, Gandolyerov 98").withMobilePhone("+79881112233")
                    .withEmail("makarov@gmail.com").withBday("1").withBmonth("January").withByear("2014"));
        }
        if (app.db().groups().isEmpty()) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

        @Test
        public void testContactAddToGroup() {
            Groups groups = app.db().groups();
            Contacts before = app.db().contacts();
            ContactData contactForAdd = before.iterator().next();
            int contactId = contactForAdd.getId();
            int groupId = groups.iterator().next().getId();
            if (isContactInGroup(groupId, contactForAdd)) {
                app.goTo().homePage();
                app.contact().removeFromGroup(contactId, groupId);
            }
            app.goTo().homePage();
            app.contact().addToGroup(contactId, groupId);
            Contacts after = app.db().contacts();
            Assert.assertTrue(isContactInGroup(groupId, getMovedContact(contactId, after)));
        }

        @Test
        public void testContactRemoveFromGroup() {
            Groups groups = app.db().groups();
            Contacts before = app.db().contacts();
            ContactData contactForRemove = before.iterator().next();
            int contactId = contactForRemove.getId();
            int groupId = groups.iterator().next().getId();
            if (!isContactInGroup(groupId, contactForRemove)) {
                app.goTo().homePage();
                app.contact().addToGroup(contactId, groupId);
            }
            app.goTo().homePage();
            app.contact().removeFromGroup(contactId, groupId);
            Contacts after = app.db().contacts();
            Assert.assertFalse(isContactInGroup(groupId, getMovedContact(contactId, after)));
        }

        private static boolean isContactInGroup(int groupId, ContactData contact) {
            return contact.getGroups().stream()
                    .map((g) -> new GroupData().withId(g.getId())).collect(Collectors.toSet())
                    .contains(new GroupData().withId(groupId));
        }

        private static ContactData getMovedContact(int contactId, Contacts contacts) {
            return contacts.stream().filter(c -> c.getId() == contactId)
                    .collect(Collectors.toSet()).iterator().next();
        }
}
