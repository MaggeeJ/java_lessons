package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetUserPasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException {
        String password = "passwordTest";
        app.pass().loginAsAdmin();
        app.pass().goToUserPage();
        String user = app.pass().getUserName();
        String email = String.format("user%s@localhost.localdomain", user);
        app.pass().resetPassword();

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String resetPasswordLink = findResetPasswordToLink(mailMessages, email);

        app.pass().changePassword(resetPasswordLink, password);
        assertTrue(app.newSession().login(user, password));
    }

    private String findResetPasswordToLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter(m -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
