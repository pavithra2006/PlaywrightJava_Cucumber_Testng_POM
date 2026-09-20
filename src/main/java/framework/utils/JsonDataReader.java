package framework.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class JsonDataReader {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonDataReader() {}

    public static List<Map<String, Object>> readArray(String resourcePath) {
        try (InputStream input = JsonDataReader.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Test data resource not found: " + resourcePath);
            }
            return MAPPER.readValue(input, new TypeReference<>() {});
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read JSON test data: " + resourcePath, exception);
        }
    }
}
