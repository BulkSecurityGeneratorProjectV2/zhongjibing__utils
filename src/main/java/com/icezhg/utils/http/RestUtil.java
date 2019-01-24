package com.icezhg.utils.http;

import java.io.IOException;
import java.util.Map;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhongjibing on 2019/01/24
 */
public class RestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtil.class);

    private static final CloseableHttpClient httpClient;
    private static final ResponseHandler<String> stringResponseHandler;

    static {
        httpClient = HttpClientFactory.simpleHttpClient();
        stringResponseHandler = new StringResponseHandler();
    }

    private static class HttpClientFactory {
        public static CloseableHttpClient defaultHttpClient() {
            return HttpClients.custom().build();
        }

        private static CloseableHttpClient simpleHttpClient() {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD)
                    .setRedirectsEnabled(false)
                    .setSocketTimeout(5000)
                    .setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .build();

            HttpClientBuilder clientBuilder = HttpClientBuilder.create();
            clientBuilder.setDefaultRequestConfig(requestConfig);

            RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
            connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);

            try {
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
                connRegistryBuilder.register("https", sslsf);
            } catch (Exception ignored) {
            }

            Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();

            clientBuilder.setConnectionManager(new PoolingHttpClientConnectionManager(connRegistry));

            return clientBuilder.build();
        }
    }

    private static class StringResponseHandler implements ResponseHandler<String> {

        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            }

            throw new ClientProtocolException("Unexpected response status: " + statusCode);
        }
    }

    private static void setParameters(RequestBuilder builder, Map<String, String> parameters) {
        if (parameters != null) {
            parameters.forEach(builder::addParameter);
        }
    }

    private static void setHeaders(RequestBuilder builder, Map<String, String> headers) {
        builder.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
    }

    private static void setEntity(RequestBuilder builder, Object content) {
        if (content != null) {
            builder.setEntity(new StringEntity(JsonUtil.toString(content), ContentType.APPLICATION_JSON));
        }

    }

    public static <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {

        CloseableHttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(request);
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
        } finally {
            httpResponse.close();
        }

        return result;
    }


    public static String get(String url) throws IOException {
        return get(url, null);
    }

    public static String get(String url, Map<String, String> parameters) throws IOException {
        return get(url, parameters, null);
    }

    public static String get(String url, Map<String, String> parameters, Map<String, String> headers) throws IOException {
        return get(url, parameters, headers, stringResponseHandler);
    }

    public static <T> T get(String url, Map<String, String> parameters, Map<String, String> headers, ResponseHandler<? extends T> responseHandler) throws IOException {
        RequestBuilder builder = RequestBuilder.get(url);
        setParameters(builder, parameters);
        setHeaders(builder, headers);

        return execute(builder.build(), responseHandler);
    }

    public static String post(String url) throws IOException {
        return post(url, null, null);
    }

    public static String post(String url, Map<String, String> headers) throws IOException {
        return post(url, null, null, headers);
    }

    public static String post(String url, Map<String, String> parameters, Object content) throws IOException {
        return post(url, parameters, content, null);
    }

    public static String post(String url, Map<String, String> parameters, Object content, Map<String, String> headers) throws IOException {
        return post(url, parameters, content, headers, stringResponseHandler);
    }

    public static <T> T post(String url, Map<String, String> parameters, Object content, Map<String, String> headers, ResponseHandler<? extends T> responseHandler) throws IOException {
        RequestBuilder builder = RequestBuilder.post(url);
        setParameters(builder, parameters);
        setHeaders(builder, headers);
        setEntity(builder, content);

        return execute(builder.build(), responseHandler);
    }
}

