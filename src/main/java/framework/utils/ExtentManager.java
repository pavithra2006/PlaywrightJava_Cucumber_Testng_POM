package framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import framework.config.ConfigReader;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentManager {
    private static ExtentReports extent;

    private ExtentManager() {}

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            try {
                Path reportDir = Path.of(ConfigReader.get("artifacts.dir"), "reports");
                Files.createDirectories(reportDir);
                ExtentSparkReporter reporter = new ExtentSparkReporter(
                        reportDir.resolve("extent-report.html").toString());
                reporter.config().setReportName("Playwright Java Automation");
                reporter.config().setDocumentTitle("Automation Results");
                extent = new ExtentReports();
                extent.attachReporter(reporter);
                extent.setSystemInfo("Environment", ConfigReader.get("environment"));
                extent.setSystemInfo("Browser", ConfigReader.get("browser"));
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to initialize Extent report", exception);
            }
        }
        return extent;
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
