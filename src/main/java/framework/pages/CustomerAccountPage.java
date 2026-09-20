package framework.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CustomerAccountPage {
    private final Page page;

    public CustomerAccountPage(Page page) { this.page = page; }

    public boolean isDisplayed() {
        return page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Logout")).isVisible();
    }

    public CustomerAccountPage deposit() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Deposit")).click();
        return this;
    }

    public CustomerAccountPage withdraw() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Withdrawl")).click();
        return this;
    }

    public CustomerAccountPage enterAmount(String amount) {
        page.getByRole(AriaRole.SPINBUTTON,
                new Page.GetByRoleOptions().setName("amount")).fill(amount);
        return this;
    }

    public void submitDeposit() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Deposit")).last().click();
    }

    public void submitWithdrawal() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Withdraw")).click();
    }

    public TransactionsPage transactions() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Transactions")).click();
        return new TransactionsPage(page);
    }
}
