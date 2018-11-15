package com.sunUtils.commos.http.async;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.*;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import java.util.*;

/**
 * 异步httpclient
 *
 * @author dengtongcai
 * @date 2018年4月23日 16:35:59
 */
public class HttpAsyncClient {
    private static Logger LOG = LoggerFactory.getLogger(HttpAsyncClient.class);

    private static int socketTimeout = 5000;
    private static int connectTimeout = 3000;
    private static int connectionRequestTimeout = 3000;
    private static int poolSize = 300;
    private static int maxPerRoute = 300;
    String connection = "close";
    private boolean isChunk = false;
    private String host = "";
    private int port = 0;
    private CloseableHttpAsyncClient asyncHttpClient;

    public HttpAsyncClient() {
        this(new Builder());
    }

    private HttpAsyncClient(Builder b) {
        socketTimeout = b.socketTimeout;
        connectTimeout = b.connectTimeout;
        poolSize = b.poolSize;
        maxPerRoute = b.maxPerRoute;
        this.connection = b.connection;
        String userAgent = b.userAgent;

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();

        SSLContext sslcontext = SSLContexts.createDefault();

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder
                .<SchemeIOSessionStrategy>create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                .register("https", new SSLIOSessionStrategy(sslcontext))
                .build();

        // 设置连接池大小
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().build();
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(ioReactor, null, sessionStrategyRegistry, null);

        if (poolSize > 0) {
            conMgr.setMaxTotal(poolSize);
        }
        if (maxPerRoute > 0) {
            conMgr.setDefaultMaxPerRoute(maxPerRoute);
        } else {
            conMgr.setDefaultMaxPerRoute(10);
        }
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).build();


        Registry<Object> authSchemeRegistry = RegistryBuilder.create()
                .register("Basic", new BasicSchemeFactory())
                .register("Digest", new DigestSchemeFactory())
                .register("NTLM", new NTLMSchemeFactory())
                .register("Negotiate", new SPNegoSchemeFactory())
                .register("Kerberos", new KerberosSchemeFactory())
                .build();

        conMgr.setDefaultConnectionConfig(connectionConfig);

        HttpAsyncClientBuilder builder = HttpAsyncClients.custom()
                .setConnectionManager(conMgr)
                .setDefaultCookieStore(new BasicCookieStore())
                .setSSLHostnameVerifier(SSLConnectionSocketFactory.getDefaultHostnameVerifier())
                .setDefaultRequestConfig(requestConfig)
                .setUserAgent(userAgent);

        builder.addInterceptorFirst(new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
            }
        });
        this.asyncHttpClient = builder.build();
    }

    public CloseableHttpAsyncClient getAsyncHttpClient() {
        return this.asyncHttpClient;
    }

    public void httpAsyncPost(String url, Map<String, String> headers, String postString, FutureCallback callback) throws Exception {
        if ((url == null) || ("".equals(url))) {
            LOG.warn("we don't have base url, check config");
            throw new Exception("missing base url");
        }

        try {
            this.asyncHttpClient.start();

            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Connection", "close");
            if (null != postString) {
                LOG.debug("exeAsyncReq post postBody={}", postString);
                StringEntity entity = new StringEntity(postString.toString(), "UTF-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            httpPost.setURI(new URI(httpPost.getURI().toString()));

            LOG.warn("exeAsyncReq getparams:" + httpPost.getURI());

            this.asyncHttpClient.execute(httpPost, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void httpAsyncPost(String url, Map<String, String> postBody, Map<String, String> header, FutureCallback callback)
            throws Exception {
        if ((url == null) || ("".equals(url))) {
            LOG.warn("we don't have base url, check config");
            throw new Exception("missing url");
        }
        try {
            HttpUriRequest request = getHttpUriRequest(url, "post", postBody, header);

            LOG.warn("exeAsyncReq post postBody={}", postBody);

            this.asyncHttpClient.execute(request, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void httpAsyncGet(String url, Map<String, String> header, FutureCallback callback) throws Exception {
        if ((url == null) || ("".equals(url))) {
            LOG.warn("we don't have base url, check config");
            throw new Exception("missing url");
        }
        try {
            this.asyncHttpClient.start();

            HttpUriRequest request = getHttpUriRequest(url, "get", null, header);

            this.asyncHttpClient.execute(request, callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void httpAsyncGet(String url, FutureCallback callback) throws Exception {
        httpAsyncGet(url, null, callback);
    }

    public static class Builder {
        private int socketTimeout = 5000;
        private int connectTimeout = 3000;
        private int poolSize = 300;
        private int maxPerRoute = 300;
        private SSLConnectionSocketFactory socketFactory;
        private String connection = "close";
        private String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36";

        public Builder setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setPoolSize(int poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public Builder setMaxPerRoute(int maxPerRoute) {
            this.maxPerRoute = maxPerRoute;
            return this;
        }

        public Builder setSocketFactory(SSLConnectionSocketFactory socketFactory) {
            this.socketFactory = socketFactory;
            return this;
        }

        public Builder setConnection(String connection) {
            this.connection = connection;
            return this;
        }

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public HttpAsyncClient build() {
            return new HttpAsyncClient();
        }
    }

    private HttpUriRequest getHttpUriRequest(String url, String method, Map<String, String> postBody, Map<String, String> headers) {
        RequestBuilder requestBuilder = buildRequestMethod(method, postBody, headers).setUri(url);
        HashMap<String, String> nowHeader = new HashMap();
        nowHeader.put("Connection", "close");
        if ((headers != null) && (headers.size() > 0)) {
            nowHeader.putAll(headers);
        }
        for (Map.Entry<String, String> headerEntry : nowHeader.entrySet()) {
            requestBuilder.addHeader((String) headerEntry.getKey(), (String) headerEntry.getValue());
        }
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setCookieSpec("best-match");
        if ((null != this.host) && (!"".equals(this.host)) && (this.port > 32)) {
            HttpHost proxy = new HttpHost(this.host, this.port);
            requestConfigBuilder.setProxy(proxy);
        }
        requestBuilder.setConfig(requestConfigBuilder.build());
        return requestBuilder.build();
    }

    protected RequestBuilder buildRequestMethod(String method, Map<String, String> postdata, Map<String, String> headers) {
        if ((method == null) || ("get".equalsIgnoreCase(method))) {
            return RequestBuilder.get();
        }
        if ("post".equalsIgnoreCase(method)) {
            RequestBuilder requestBuilder = RequestBuilder.post();
            if (postdata != null) {
                String contentType = "application/x-www-form-urlencoded; charset=UTF-8";


                String charset = "";
                Iterator localIterator;
                if ((headers != null) && (headers.size() > 0)) {
                    charset = (String) headers.remove("charset");
                    for (localIterator = headers.keySet().iterator(); localIterator.hasNext(); ) {
                        String ksy = (String) localIterator.next();
                        if ("Content-Type".equalsIgnoreCase(ksy)) {
                            contentType = ((String) headers.get(ksy)).toLowerCase().trim();
                            break;
                        }
                    }
                }
                String ksy;
                if (((null == charset) || ("".equals(charset)) || (!checkCharset(charset))) &&
                        (contentType.contains("charset"))) {
                    String ncharset = contentType.substring(contentType.lastIndexOf("=") + 1, contentType.length());
                    if (checkCharset(ncharset)) {
                        charset = ncharset;
                    }
                }
                if ((null == charset) || ("".equals(charset)) || (!checkCharset(charset))) {
                    charset = "UTF-8";
                }
                if (("".equals(contentType)) || (contentType.toLowerCase().contains("x-www-form-urlencoded"))) {
                    Object formParams = new ArrayList();
                    for (String str : postdata.keySet()) {
                        NameValuePair n = new BasicNameValuePair(str, (String) postdata.get(str));
                        ((List) formParams).add(n);
                    }
                    UrlEncodedFormEntity entity = null;
                    try {
                        entity = new UrlEncodedFormEntity((List) formParams, charset);
                        if (this.isChunk) {
                            entity.setChunked(true);
                        }
                    } catch (UnsupportedEncodingException e) {
                        LOG.error(e.getMessage(), e);
                    }
                    requestBuilder.setEntity(entity);
                } else {
                    LOG.info("post Content-Type : [ " + contentType + " ] , pay attention to it .");
                    String pstdata = (String) postdata.get("postdata");
                    if (("".equals(pstdata)) || (pstdata == null)) {
                        pstdata = (String) postdata.get("");
                    }
                    if (pstdata == null) {
                        pstdata = "";
                    }
                    StringEntity entity = new StringEntity(pstdata, charset);
                    entity.setContentType(contentType);
                    if (this.isChunk) {
                        entity.setChunked(true);
                    }
                    requestBuilder.setEntity(entity);
                }
            } else {
                LOG.warn("The Method Is Post,But No Post Data .");
            }
            return requestBuilder;
        }
        if ("head".equalsIgnoreCase(method)) {
            return RequestBuilder.head();
        }
        if ("put".equalsIgnoreCase(method)) {
            return RequestBuilder.put();
        }
        if ("delete".equalsIgnoreCase(method)) {
            return RequestBuilder.delete();
        }
        if ("trace".equalsIgnoreCase(method)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    private static boolean checkCharset(String charset) {
        boolean b = false;
        try {
            b = Charset.isSupported(charset);
        } catch (IllegalCharsetNameException e) {
            LOG.error(e.getMessage());
        }
        return b;
    }
}
