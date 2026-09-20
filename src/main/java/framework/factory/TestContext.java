package framework.factory;

import com.microsoft.playwright.Page;
import framework.pages.LoginPage;

public final class TestContext {
    private LoginPage loginPage;

    public void initialize() {
        PlaywrightFactory.initialize();
        loginPage = new LoginPage(PlaywrightFactory.page());
    }

    public Page page() {
        return PlaywrightFactory.page();
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            throw new IllegalStateException("TestContext has not been initialized");
        }
        return loginPage;
    }

    public void close(boolean failed) {
        PlaywrightFactory.close(failed);
        loginPage = null;
    }
}
