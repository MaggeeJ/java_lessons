package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase{

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupsHelper().isThereAGroup()) {
            app.getGroupsHelper().createGroup (new GroupData("test1", "test2", "test3"));
        }
        int before = app.getGroupsHelper().getGroupCount();
        app.getGroupsHelper().selectGroup();
        app.getGroupsHelper().initGroupModification();
        app.getGroupsHelper().fillGroupForm(new GroupData("Отличники", "Самые умные", "Азиаты"));
        app.getGroupsHelper().submitGroupModification();
        app.getGroupsHelper().returnToGroupPage();
        int after = app.getGroupsHelper().getGroupCount();
        Assert.assertEquals(after, before);
    }
}
