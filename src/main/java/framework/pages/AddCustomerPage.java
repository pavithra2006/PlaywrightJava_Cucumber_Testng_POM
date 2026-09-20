package framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AddCustomerPage {
    private final Page page;

    public AddCustomerPage(Page page) { this.page = page; }

    public AddCustomerPage enterCustomer(String firstName, String lastName, String postCode) {
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("First Name")).fill(firstName);
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Last Name")).fill(lastName);
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Post Code")).fill(postCode);
        return this;
    }

    public String submit() {
        java.util.concurrent.atomic.AtomicReference<String> message = new java.util.concurrent.atomic.AtomicReference<>();
        page.onceDialog(dialog -> {
            message.set(dialog.message());
            dialog.accept();
        });
        page.locator("form").getByRole(AriaRole.BUTTON,
                new Locator.GetByRoleOptions().setName("Add Customer")).click();
        return message.get();
    }
}
