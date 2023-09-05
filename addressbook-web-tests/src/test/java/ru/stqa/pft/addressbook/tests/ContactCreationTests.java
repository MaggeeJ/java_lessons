package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase  {

  @Test(enabled = true)
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resourses/stru.png");
    ContactData contact = new ContactData()
            .withFirstname("Anton").withMiddlename("Vasil").withLastname("Makarov").withNickname("Pups").withPhoto(photo)
            .withCompany("Groovy").withAddress("Kazahstan, Gandolyerov 98").withMobilePhone("+79881112233")
            .withEmail("makarov@gmail.com").withBday("1").withBmonth("January").withByear("2014").withGroup("ghjdjdsdfs");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

//  @Test
//  public void testCurrentDir() {
//    File currentDir = new File(".");
//    System.out.println(currentDir.getAbsolutePath());
//    File photo = new File("src/test/resourses/stru.png");
//    System.out.println(photo.getAbsolutePath());
//    System.out.println(photo.exists());
//
//  }

}
