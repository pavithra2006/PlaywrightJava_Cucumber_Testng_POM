package framework.factory;

import com.microsoft.playwright.*;
import framework.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public final class PlaywrightFactory {
    private static final Logger LOG = LoggerFactory.getLogger(PlaywrightFactory.class);
    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();
    private static final ThreadLocal<Path> RUN_DIR = new ThreadLocal<>();
    private static final AtomicLong RUN_SEQUENCE = new AtomicLong();

    private PlaywrightFactory() {}

    public static void initialize() {
        Path runDir = Path.of(ConfigReader.get("artifacts.dir"), timestamp());
        try {
            Files.createDirectories(runDir.resolve("screenshots"));
            Files.createDirectories(runDir.resolve("traces"));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to create test-results directories", exception);
        }
        Playwright playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(ConfigReader.getBoolean("headless"));
        Browser browser = switch (ConfigReader.get("browser").toLowerCase()) {
            case "chromium" -> playwright.chromium().launch(launchOptions);
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> throw new IllegalArgumentException("browser must be chromium, firefox, or webkit");
        };
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        if (ConfigReader.getBoolean("record.video")) {
            try {
                Files.createDirectories(runDir.resolve("videos"));
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to create video directory", exception);
            }
            contextOptions.setRecordVideoDir(runDir.resolve("videos"));
        }
        if (ConfigReader.getBoolean("record.har")) {
            contextOptions.setRecordHarPath(runDir.resolve("network.har"));
        }
        BrowserContext context = browser.newContext(contextOptions);
        if (ConfigReader.getBoolean("trace.on.failure")) {
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true).setSnapshots(true).setSources(true));
        }
        Page page = context.newPage();
        page.setDefaultTimeout(ConfigReader.getInt("timeout.ms"));
        page.setDefaultNavigationTimeout(ConfigReader.getInt("action.timeout.ms"));
        page.onConsoleMessage(message -> LOG.info("Browser console [{}]: {}", message.type(), message.text()));
        page.onRequest(request -> LOG.debug("Request {} {}", request.method(), request.url()));
        page.onResponse(response -> {
            if (response.status() >= 400) LOG.warn("HTTP {} {}", response.status(), response.url());
        });
        PLAYWRIGHT.set(playwright);
        BROWSER.set(browser);
        CONTEXT.set(context);
        PAGE.set(page);
        RUN_DIR.set(runDir);
        LOG.info("Initialized {} browser in {} environment", ConfigReader.get("browser"),
                ConfigReader.get("environment"));
    }

    public static Page page() {
        Page page = PAGE.get();
        if (page == null) throw new IllegalStateException("Playwright page is not initialized");
        return page;
    }

    public static BrowserContext context() {
        return CONTEXT.get();
    }

    public static Path screenshot(String name) {
        Path path = RUN_DIR.get().resolve("screenshots").resolve(name + ".png");
        page().screenshot(new Page.ScreenshotOptions().setPath(path).setFullPage(true));
        return path;
    }

    public static Path tracePath() {
        return RUN_DIR.get().resolve("traces").resolve("trace.zip");
    }

    public static void close(boolean failed) {
        BrowserContext context = CONTEXT.get();
        if (context != null) {
            if (ConfigReader.getBoolean("trace.on.failure") && failed) {
                context.tracing().stop(new Tracing.StopOptions().setPath(tracePath()));
            } else if (ConfigReader.getBoolean("trace.on.failure")) {
                context.tracing().stop();
            }
            context.close();
        }
        closeThreadLocal(BROWSER);
        closeThreadLocal(PLAYWRIGHT);
        CONTEXT.remove();
        PAGE.remove();
        RUN_DIR.remove();
    }

    private static <T extends AutoCloseable> void closeThreadLocal(ThreadLocal<T> holder) {
        T resource = holder.get();
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception exception) {
                LOG.warn("Playwright resource was already closed during cleanup", exception);
            } finally {
                holder.remove();
            }
        }
    }

    private static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS"))
                + "-" + RUN_SEQUENCE.incrementAndGet();
    }
}
