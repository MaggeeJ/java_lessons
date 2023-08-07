package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Anton", "Vasil", "Makarov",
                    "Pups", "Groovy", "Kazahstan, Gandolyerov 98", "+79881112233",
                    "makarov@gmail.com", "1", "January", "2014", "test1"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Иван", "Иванович", "Иванов",
                "Петр", "Петруздель", "Петрозаводск, Петроградня 206", "+79889996677",
                "tt@gmail.com", "6", "May", "1996", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
    }
}
