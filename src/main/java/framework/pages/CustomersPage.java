package framework.pages;

import com.microsoft.playwright.Page;

public class CustomersPage {
    private final Page page;

    public CustomersPage(Page page) { this.page = page; }

    public CustomersPage search(String customer) {
        page.getByRole(com.microsoft.playwright.options.AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Search Customer")).fill(customer);
        return this;
    }

    public boolean containsCustomer(String firstName, String lastName) {
        return page.locator("table").getByText(firstName,
                        new com.microsoft.playwright.Locator.GetByTextOptions().setExact(true)).isVisible()
                && page.locator("table").getByText(lastName,
                        new com.microsoft.playwright.Locator.GetByTextOptions().setExact(true)).isVisible();
    }
}
