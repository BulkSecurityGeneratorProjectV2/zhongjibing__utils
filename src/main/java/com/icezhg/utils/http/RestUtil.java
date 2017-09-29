package com.icezhg.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtil.class);

    private static final HttpClient CLIENT;

    static {
        CLIENT = HttpClientFactory.simpleHttpClient();
    }

    private RestUtil() {
    }

    public static String execute(HttpUriRequest request) throws IOException {

        HttpResponse httpResponse;
        try {
            httpResponse = CLIENT.execute(request);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        StringBuilder result = new StringBuilder();
        try (InputStream stream = httpResponse.getEntity().getContent();
             BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return result.toString();
    }

    public static <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {

        HttpResponse httpResponse;
        try {
            httpResponse = CLIENT.execute(request);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        T result;
        try {
            result = responseHandler.handleResponse(httpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

        return result;
    }
}
