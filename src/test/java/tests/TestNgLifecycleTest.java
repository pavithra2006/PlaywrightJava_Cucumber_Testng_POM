package tests;

import framework.factory.TestContext;
import org.testng.Assert;
import org.testng.annotations.*;
import framework.utils.RetryAnalyzer;

public class TestNgLifecycleTest {
    private TestContext context;

    @BeforeClass
    public void beforeClass() {
        context = new TestContext();
    }

    @BeforeMethod
    public void beforeMethod() {
        context.initialize();
    }

    @Test(groups = {"ui", "smoke"}, retryAnalyzer = RetryAnalyzer.class)
    public void loginPageIsAvailable() {
        Assert.assertTrue(context.loginPage().open().isDisplayed(),
                "GlobalSQA login page is not displayed");
    }

    @AfterMethod
    public void afterMethod() {
        context.close(false);
    }

    @AfterClass
    public void afterClass() {
        context = null;
    }
}
