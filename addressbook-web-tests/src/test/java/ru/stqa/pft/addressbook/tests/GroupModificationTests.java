package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase{

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupsHelper().isThereAGroup()) {
            app.getGroupsHelper().createGroup (new GroupData("test1", "test2", "test3"));
        }
        List<GroupData> before = app.getGroupsHelper().getGroupList();
        app.getGroupsHelper().selectGroup(before.size() - 1);
        app.getGroupsHelper().initGroupModification();
        GroupData group = new GroupData(before.get(before.size() - 1).getId(), "Отличники", "Самые умные", "Азиаты");
        app.getGroupsHelper().fillGroupForm(group);
        app.getGroupsHelper().submitGroupModification();
        app.getGroupsHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupsHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(group);
        Assert.assertEquals(new HashSet<>(before), new HashSet<>(after));
    }
}
