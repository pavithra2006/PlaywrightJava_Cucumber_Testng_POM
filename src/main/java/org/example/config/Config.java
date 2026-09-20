package org.example.config;

public final class Config {
    private Config() {}

    public static String baseUrl() {
        return System.getProperty("baseUrl",
                "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "true"));
    }

    public static int timeoutMs() {
        return Integer.parseInt(System.getProperty("timeoutMs", "15000"));
    }
}
