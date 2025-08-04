package model.settings;

import com.codeborne.selenide.Configuration;

public class BrowserConfig {

public static void setupBrowser(Browser browser) {
    switch (browser) {
        case CHROME:
            Configuration.browser = "chrome";
            System.setProperty("webdriver.chrome.driver", "driver/chrome/chromedriver");
            break;
        case YANDEX:
            Configuration.browser = "chrome";
            System.setProperty("webdriver.chrome.driver", "driver/yandex/yandexdriver");
            break;
        default:
            Configuration.browser = "chrome";
    }
}
}
