# Playwright Java Automation Framework

Beginner-friendly, interview-ready UI and API automation for the GlobalSQA XYZ Bank demo.

## Stack

Java 17, Maven, Playwright Java, TestNG, Cucumber BDD, Page Object Model, SLF4J/Logback, ExtentReports, Jackson, JDBC, and GitHub Actions.

## Structure

```text
src/main/java/framework/
  api/       APIRequestContext client
  config/    ConfigReader
  factory/   ThreadLocal Playwright lifecycle
  pages/     Login, manager, customer, account, transaction pages
  utils/     Database and reporting utilities
src/main/resources/
  config/config.properties
  logback.xml
src/test/java/
  hooks/          Cucumber lifecycle and failure evidence
  runners/        TestNG Cucumber runner
  stepdefinitions/BDD steps
  tests/          non-Cucumber API tests
src/test/resources/features/manager.feature
test-results/     screenshots, traces, videos, HAR, logs, reports
```

## Prerequisites and installation

Install JDK 17+, Maven 3.9+, Git, and Node.js only if you want to open traces with the Playwright CLI.

From the project root:

```powershell
mvn clean test-compile
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
```

Install another browser when needed:

```powershell
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install firefox"
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install webkit"
```

## Running tests

Run UI and API tests through the suite. Local configuration is headed by default so beginners can watch the test:

```powershell
mvn clean test
```

Run one browser or headed mode:

```powershell
mvn clean test -Dbrowser=chromium
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=webkit
mvn clean test -Dheadless=false
```

To force a local headless run:

```powershell
mvn clean test -Dheadless=true
```

Run Cucumber tags:

```powershell
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@regression"
mvn test -Dcucumber.filter.tags="@ui and @smoke"
```

Run API tests only:

```powershell
mvn test -Dtest=tests.ApiSmokeTest
```

Useful configuration overrides:

```powershell
mvn test -Dtimeout.ms=30000 -Denvironment=staging
mvn test -Denv=qa
mvn test -Drecord.video=true -Drecord.har=true
mvn test -Dtrace.on.failure=false -Dscreenshot.on.failure=false
```

Optional TestNG parallel execution (safe for classes using isolated state):

```powershell
mvn test -Dsurefire.suiteXmlFiles=testng-parallel.xml
```

## Reports and evidence

- Cucumber HTML/JSON: `test-results/reports/`
- Extent HTML: `test-results/reports/extent-report.html`
- Failure screenshots: `test-results/<run-id>/screenshots/`
- Traces: `test-results/<run-id>/traces/trace.zip`
- Optional video and HAR: `test-results/<run-id>/videos/` and `network.har`
- Logs: `test-results/logs/`
- The Extent report opens automatically after a local Maven run. Set
  `-Dreport.auto.open=false` when running from a headless environment.

Open a trace with:

```powershell
npx playwright show-trace test-results\<run-id>\traces\trace.zip
```

## Viewing reports locally

After `mvn clean test`, the Extent report opens automatically. To open either
report manually from the project root:

```powershell
Start-Process .\test-results\reports\extent-report.html
Start-Process .\test-results\reports\cucumber.html
```

If a failure produced a trace:

```powershell
npx playwright show-trace .\test-results\<run-id>\traces\trace.zip
```

Screenshots and videos can be opened directly from the matching `test-results\<run-id>` folder.

## Viewing reports in GitHub Actions

The workflow supports manual execution through **Actions -> Playwright Java tests -> Run workflow**. It always runs headless in CI and uploads `test-results/` as the `playwright-test-results` artifact, even when tests fail.

After the run completes:

1. Open the workflow run in GitHub.
2. Scroll to **Artifacts**.
3. Download `playwright-test-results`.
4. Extract it and open `reports/extent-report.html` for the senior-QA summary
   (status, tags, environment, and failure evidence), or
   `reports/cucumber.html` for the BDD view.
5. Open a trace with `npx playwright show-trace <path-to-trace.zip>`.

## API and database examples

`framework.api.ApiClient` uses Playwright `APIRequestContext` and demonstrates GET, POST, PUT, DELETE against JSONPlaceholder. This is intentionally independent from XYZ Bank because the public UI demo does not expose a verified public API.

`framework.utils.DatabaseUtils` supports parameterized `query`, `update`, and `delete` operations with JDBC and try-with-resources.

## CI

`.github/workflows/playwright-tests.yml` checks out the repository, installs Java and Chromium, runs Maven tests, and uploads reports/evidence. See `ARCHITECTURE.md` for design decisions and parallel-execution guidance.
