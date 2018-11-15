package com.sunUtils.commos.http.factory;

import com.sunUtils.commos.http.async.HttpAsyncClient;
import com.sunUtils.commos.http.sync.HttpSyncClient;

public class HttpClientFactory
{
    private static HttpAsyncClient httpAsyncClient = new HttpAsyncClient();
    private static HttpSyncClient httpSyncClient = new HttpSyncClient();
    private static HttpClientFactory httpClientFactory = new HttpClientFactory();

    public static HttpClientFactory getInstance()
    {
        return httpClientFactory;
    }

    public HttpAsyncClient getHttpAsyncClientPool()
    {
        return httpAsyncClient;
    }

    public HttpSyncClient getHttpSyncClientPool()
    {
        return httpSyncClient;
    }
}
