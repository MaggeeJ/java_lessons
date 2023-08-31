package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase  {

  @Test(enabled = true)
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData()
            .withFirstname("Anton").withMiddlename("Vasil").withLastname("Makarov").withNickname("Pups")
            .withCompany("Groovy").withAddress("Kazahstan, Gandolyerov 98").withMobile("+79881112233")
            .withEmail("makarov@gmail.com").withBday("1").withBmonth("January").withByear("2014").withGroup("ghjdjdsdfs");
    app.contact().create(contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
