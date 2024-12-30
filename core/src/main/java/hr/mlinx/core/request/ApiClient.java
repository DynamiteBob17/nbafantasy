package hr.mlinx.core.request;

import hr.mlinx.core.exception.HttpRequestFailedException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ApiClient {
    private final String baseUrlString;

    public ApiClient(String baseUrlString) {
        this.baseUrlString = baseUrlString;
    }

    public JSONObject get(String endpoint, QueryParam<?, ?>... queryParams) throws IOException, HttpRequestFailedException {
        String urlWithParams = getUrlStringWithQueryParams(baseUrlString + endpoint, queryParams);

        URL url = URI.create(urlWithParams).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        return getResponse(connection);
    }

    private String getUrlStringWithQueryParams(String urlString, QueryParam<?, ?>... queryParams) throws UnsupportedEncodingException {
        if (queryParams == null || queryParams.length == 0) {
            return urlString;
        }

        StringBuilder urlWithParams = new StringBuilder(urlString);
        boolean firstParam = true;
        for (QueryParam<?, ?> queryParam : queryParams) {
            String encodedKey = URLEncoder.encode(queryParam.key().toString(), StandardCharsets.UTF_8);
            String encodedValue = URLEncoder.encode(queryParam.value().toString(), StandardCharsets.UTF_8);

            if (firstParam) {
                urlWithParams.append("?");
                firstParam = false;
            } else {
                urlWithParams.append("&");
            }

            urlWithParams.append(encodedKey).append("=").append(encodedValue);
        }

        return urlWithParams.toString();
    }

    private JSONObject getResponse(HttpURLConnection connection) throws IOException, HttpRequestFailedException {
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder res = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    res.append(line);
                }
                return new JSONObject(res.toString());
            }
        } else {
            throw new HttpRequestFailedException(responseCode);
        }
    }
}
