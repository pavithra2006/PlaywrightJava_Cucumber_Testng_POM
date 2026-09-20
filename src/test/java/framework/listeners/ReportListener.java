package framework.listeners;

import framework.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportListener implements IExecutionListener {
    private static final Logger LOG = LoggerFactory.getLogger(ReportListener.class);

    @Override
    public void onExecutionFinish() {
        if (!ConfigReader.getBoolean("report.auto.open")) {
            return;
        }

        Path reportDirectory = Path.of(ConfigReader.get("artifacts.dir"), "reports").toAbsolutePath();
        Path report = reportDirectory.resolve("extent-report.html");
        if (!Files.isRegularFile(report)) {
            report = reportDirectory.resolve("cucumber.html");
        }
        if (!Files.isRegularFile(report)) {
            LOG.warn("No HTML report was found in {}", reportDirectory);
            return;
        }

        if (!Desktop.isDesktopSupported()) {
            LOG.warn("Desktop integration is unavailable; open the report manually at {}", report);
            return;
        }

        try {
            Desktop.getDesktop().open(report.toFile());
            LOG.info("Opened report: {}", report);
        } catch (IOException exception) {
            LOG.warn("Unable to open report automatically; open it manually at {}", report, exception);
        }
    }
}
