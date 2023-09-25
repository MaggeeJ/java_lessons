package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static org.openqa.selenium.remote.Browser.*;

public class ApplicationManager {
    private final String browser;
    private final Properties properties;
    private WebDriver wd;
    private RegistrationHelper registrationHelper
            ;
    private FtpHelper ftp;
    private MailHelper mailHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();

    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if (wd != null){
            wd.quit();
        }
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public FtpHelper ftp() {
        if (ftp == null) {
            ftp = new FtpHelper(this);
        }
        return ftp;
    }

    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public WebDriver getDriver() {
        if (wd == null) {
            if (browser.equals(FIREFOX.browserName())) {
                wd = new FirefoxDriver();
            } else if (browser.equals(CHROME.browserName())) {
                wd = new ChromeDriver();
            } else if (browser.equals(IE.browserName())) {
                wd = new InternetExplorerDriver();
            }
            wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            wd.get(properties.getProperty("web.baseURL"));
        }
        return wd;
    }
}
