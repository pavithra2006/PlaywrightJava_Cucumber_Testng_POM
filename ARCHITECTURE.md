# Framework architecture

The framework deliberately uses a small number of easy-to-explain components:

- **Playwright** provides browser automation, auto-waiting, tracing, network events, and `APIRequestContext`.
- **Page Object Model** keeps locators and user actions in `framework.pages`; Cucumber steps express business intent.
- **Factory** owns the Playwright -> Browser -> Context -> Page lifecycle. ThreadLocal holders isolate parallel scenarios without exposing global Page objects.
- **TestContext** is the small per-scenario fixture that initializes the factory and first page object, then closes the isolated context.
- **Cucumber + TestNG** gives readable BDD scenarios and a standard TestNG runner. API tests remain ordinary TestNG tests.
- **Evidence** is captured only on failures by default: screenshots, Cucumber attachments, and traces. Video/HAR can be enabled by properties.
- **ExtentReports** combines test status with environment/browser metadata and failure evidence.
- **CI** uses Maven, Java 17, browser installation, and artifact upload in GitHub Actions.

Parallel execution can be enabled later by configuring Cucumber/TestNG parallelism. Each scenario must keep its own factory ThreadLocal state and must not share mutable page objects.
