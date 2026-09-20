package org.example.driver;

import com.microsoft.playwright.*;
import org.example.config.Config;

public final class PlaywrightManager {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    private PlaywrightManager() {}

    public static void start() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(Config.headless()));
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(Config.timeoutMs());
    }

    public static Page page() {
        if (page == null) {
            throw new IllegalStateException("Playwright has not been started.");
        }
        return page;
    }

    public static void stop() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        page = null;
        context = null;
        browser = null;
        playwright = null;
    }
}
