package framework.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.function.BooleanSupplier;

public final class WaitUtils {
    private WaitUtils() {}

    public static void forVisible(Locator locator) {
        locator.waitFor();
    }

    public static void forUrl(Page page, String url) {
        page.waitForURL(url);
    }

    public static void forCondition(Page page, BooleanSupplier condition) {
        page.waitForCondition(condition);
    }
}
