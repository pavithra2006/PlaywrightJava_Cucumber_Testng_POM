package framework.pages;

import com.microsoft.playwright.Page;

public class TransactionsPage {
    private final Page page;

    public TransactionsPage(Page page) { this.page = page; }

    public boolean isDisplayed() {
        return page.getByRole(com.microsoft.playwright.options.AriaRole.TABLE).isVisible();
    }
}
