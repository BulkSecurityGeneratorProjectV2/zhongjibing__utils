package com.icezhg.utils.http;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Created by zhongjibing on 2017/5/11.
 */
public class HttpClientFactory {
    public static CloseableHttpClient defaultHttpClient() {
        return HttpClients.custom().build();
    }

    public static CloseableHttpClient simpleHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .setRedirectsEnabled(false)
                .setSocketTimeout(20000)
                .setConnectTimeout(20000)
                .build();

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultRequestConfig(requestConfig);
        clientBuilder.setConnectionManager(new PoolingHttpClientConnectionManager());

        return clientBuilder.build();
    }
}
