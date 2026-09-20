package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"framework.stepdefinitions", "framework.hooks"},
        plugin = {"pretty", "html:test-results/reports/cucumber.html",
                "json:test-results/reports/cucumber.json"},
        monochrome = true
)
public class BankingTestRunner extends AbstractTestNGCucumberTests {
}
