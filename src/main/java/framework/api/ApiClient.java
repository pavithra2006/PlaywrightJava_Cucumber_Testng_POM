package framework.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import framework.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class ApiClient implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(ApiClient.class);
    private final Playwright playwright;
    private final APIRequestContext request;

    public ApiClient() {
        playwright = Playwright.create();
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ConfigReader.get("api.base.url"))
                .setExtraHTTPHeaders(Map.of("Accept", "application/json")));
    }

    public APIResponse get(String path) {
        LOG.info("GET {}", path);
        return request.get(path);
    }

    public APIResponse post(String path, String json) {
        LOG.info("POST {}", path);
        return request.post(path, RequestOptions.create().setHeader("Content-Type", "application/json").setData(json));
    }

    public APIResponse put(String path, String json) {
        LOG.info("PUT {}", path);
        return request.put(path, RequestOptions.create().setHeader("Content-Type", "application/json").setData(json));
    }

    public APIResponse delete(String path) {
        LOG.info("DELETE {}", path);
        return request.delete(path);
    }

    @Override
    public void close() {
        request.dispose();
        playwright.close();
    }
}
