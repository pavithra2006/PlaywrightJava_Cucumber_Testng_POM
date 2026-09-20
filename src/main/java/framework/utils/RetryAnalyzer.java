package framework.utils;

import framework.config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int attempts;

    @Override
    public boolean retry(ITestResult result) {
        int maxRetries = Integer.parseInt(System.getProperty("maxRetries", "1"));
        return !result.isSuccess() && attempts++ < maxRetries;
    }
}
