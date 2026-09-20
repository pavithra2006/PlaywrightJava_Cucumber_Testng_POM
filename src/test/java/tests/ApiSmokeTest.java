package tests;

import com.microsoft.playwright.APIResponse;
import framework.api.ApiClient;
import org.testng.Assert;
import org.testng.annotations.Test;
import framework.utils.RetryAnalyzer;

public class ApiSmokeTest {
    @Test(groups = {"api", "smoke"}, retryAnalyzer = RetryAnalyzer.class)
    public void getPostReturnsExpectedResource() {
        try (ApiClient client = new ApiClient()) {
            APIResponse response = client.get("/posts/1");
            Assert.assertEquals(response.status(), 200);
            Assert.assertTrue(response.text().contains("\"id\": 1"));
        }
    }

    @Test(groups = {"api", "regression"})
    public void createUpdateAndDeletePost() {
        try (ApiClient client = new ApiClient()) {
            APIResponse created = client.post("/posts", "{\"title\":\"qa\",\"body\":\"playwright\",\"userId\":1}");
            Assert.assertEquals(created.status(), 201);
            APIResponse updated = client.put("/posts/1", "{\"id\":1,\"title\":\"updated\"}");
            Assert.assertEquals(updated.status(), 200);
            APIResponse deleted = client.delete("/posts/1");
            Assert.assertEquals(deleted.status(), 200);
        }
    }
}
