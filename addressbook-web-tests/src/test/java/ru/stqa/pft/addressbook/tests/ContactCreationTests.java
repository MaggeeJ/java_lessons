package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase  {

  @Test(enabled = true)
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Set<ContactData> before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Anton").withMiddlename("Vasil").withLastname("Makarov").withNickname("Pups")
            .withCompany("Groovy").withAddress("Kazahstan, Gandolyerov 98").withMobile("+79881112233")
            .withEmail("makarov@gmail.com").withBday("1").withBmonth("January").withByear("2014").withGroup("ghjdjdsdfs");
    app.contact().create(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
