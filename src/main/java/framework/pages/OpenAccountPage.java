package framework.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class OpenAccountPage {
    private final Page page;

    public OpenAccountPage(Page page) { this.page = page; }

    public OpenAccountPage selectCustomer(String name) {
        page.locator("select").nth(0).selectOption(new SelectOption().setLabel(name));
        return this;
    }

    public OpenAccountPage selectCurrency(String currency) {
        page.locator("select").nth(1).selectOption(new SelectOption().setLabel(currency));
        return this;
    }

    public void process() {
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Process")).click();
    }
}
