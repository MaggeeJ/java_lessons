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
    WebDriver wd;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();

    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

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

    public void stop() {
        wd.quit();
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
