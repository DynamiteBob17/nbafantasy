package hr.mlinx.core.request.factory;

import hr.mlinx.core.initializer.DataInitializer;
import hr.mlinx.core.request.ApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiClientFactory {
    private ApiClientFactory() {
    }

    public static ApiClient createApiClientFromProperties() throws IOException {
        try (InputStream is = DataInitializer.class.getClassLoader().getResourceAsStream("env.properties")) {
            if (is == null) {
                throw new IOException("Cannot load resources");
            }

            Properties properties = new Properties();
            properties.load(is);

            String apiOriginUrl = properties.getProperty("API_ORIGIN_URL");
            if (apiOriginUrl == null) {
                throw new IllegalArgumentException("Missing API_ORIGIN_URL in properties");
            }

            return new ApiClient(apiOriginUrl);
        }
    }
}
