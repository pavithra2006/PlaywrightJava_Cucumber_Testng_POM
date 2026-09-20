package framework.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UiActions {
    private static final Logger LOG = LoggerFactory.getLogger(UiActions.class);

    private UiActions() {}

    public static void click(Page page, Locator element, String description) {
        LOG.info("Clicking {}", description);
        element.click();
    }

    public static void setValue(Page page, Locator element, String value, String description) {
        LOG.info("Setting {}", description);
        element.fill(value);
    }

    public static void selectByLabel(Locator element, String label, String description) {
        LOG.info("Selecting {}: {}", description, label);
        element.selectOption(new com.microsoft.playwright.options.SelectOption().setLabel(label));
    }

    public static String text(Locator element, String description) {
        LOG.debug("Reading {}", description);
        return element.innerText();
    }

    public static boolean isVisible(Locator element, String description) {
        LOG.debug("Checking visibility of {}", description);
        return element.isVisible();
    }

    public static void waitForVisible(Locator element, String description) {
        LOG.debug("Waiting for {}", description);
        element.waitFor();
    }
}
