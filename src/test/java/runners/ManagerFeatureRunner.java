package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/manager.feature",
        glue = {"framework"},
        plugin = {"pretty", "html:test-results/reports/manager-cucumber.html",
                "json:test-results/reports/manager-cucumber.json"},
        monochrome = true
)
public class ManagerFeatureRunner extends AbstractTestNGCucumberTests {
}
