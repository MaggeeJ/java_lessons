package ru.stqa.pft.mantis.tests;


import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import static org.openqa.selenium.remote.Browser.FIREFOX;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager(
            System.getProperty("browser", FIREFOX.browserName()));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
        app.ftp().upload(new File("C:/Users/jmako/Documents/GitHub/java_lessons/mantis-tests/src/test/resources/config_inc.php"),"config_inc.php","config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.ftp().restore("config_inc.php.bak","config_inc.php");
        app.stop();
    }

    public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    public boolean isIssueOpen(int issueId) throws RemoteException, MalformedURLException, ServiceException {
        MantisConnectPortType mc = app.soap().getMantisConnect();
        IssueData issuedata = mc.mc_issue_get("administrator","root", BigInteger.valueOf(issueId));
        String issueStatus = issuedata.getStatus().getName();
        return !issueStatus.equals("closed");
    }

}
