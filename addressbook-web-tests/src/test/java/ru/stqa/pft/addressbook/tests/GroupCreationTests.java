package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupsHelper().initGroupCreation();
        app.getGroupsHelper().fillGroupForm(new GroupData("test4", "test5", "test6"));
        app.getGroupsHelper().submitGroupCreation();
        app.getNavigationHelper().gotoGroupPage();
    }

}