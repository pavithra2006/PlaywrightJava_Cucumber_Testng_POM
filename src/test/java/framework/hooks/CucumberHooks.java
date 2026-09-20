package framework.hooks;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import framework.factory.PlaywrightFactory;
import framework.factory.TestContext;
import framework.utils.ExtentManager;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CucumberHooks {
    private static final Logger LOG = LoggerFactory.getLogger(CucumberHooks.class);
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();
    private static final ThreadLocal<TestContext> CONTEXT = new ThreadLocal<>();

    @Before
    public void beforeScenario(Scenario scenario) {
        TestContext context = new TestContext();
        context.initialize();
        CONTEXT.set(context);
        CURRENT_TEST.set(ExtentManager.getExtent().createTest(scenario.getName()));
        CURRENT_TEST.get().assignCategory(scenario.getSourceTagNames().toArray(new String[0]));
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.getStatus() == Status.FAILED) {
            captureFailure(scenario, "step-failure");
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                captureFailure(scenario, "scenario-failure");
                CURRENT_TEST.get().fail("Scenario failed");
            } else {
                CURRENT_TEST.get().pass("Scenario passed");
            }
        } finally {
            CONTEXT.get().close(scenario.isFailed());
            ExtentManager.flush();
            CURRENT_TEST.remove();
            CONTEXT.remove();
        }
    }

    private void captureFailure(Scenario scenario, String label) {
        try {
            byte[] screenshot = PlaywrightFactory.page().screenshot(
                    new Page.ScreenshotOptions().setFullPage(true));
            scenario.attach(screenshot, "image/png", label);
            java.nio.file.Path path = PlaywrightFactory.screenshot(label + "-" + System.nanoTime());
            CURRENT_TEST.get().fail("Failure evidence: " + path);
        } catch (RuntimeException exception) {
            LOG.error("Unable to capture failure evidence", exception);
            CURRENT_TEST.get().fail(exception);
        }
    }
}
