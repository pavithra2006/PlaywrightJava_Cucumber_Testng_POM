package org.example.pages;

import com.microsoft.playwright.Page;

public class CustomerPage {
    private final Page page;

    public CustomerPage(Page page) {
        this.page = page;
    }

    public boolean isLoggedIn() {
        return page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Logout")).isVisible();
    }

    public String welcomeText() {
        return page.locator("body").innerText();
    }
}
