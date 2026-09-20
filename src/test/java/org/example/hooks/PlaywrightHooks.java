package org.example.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.example.driver.PlaywrightManager;

public class PlaywrightHooks {
    @Before
    public void startBrowser() {
        PlaywrightManager.start();
    }

    @After
    public void closeBrowser() {
        PlaywrightManager.stop();
    }
}
