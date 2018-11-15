package com.sunUtils.commos.http.sync;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 封装的httpclient
 *
 * @author xym
 * @date 2015年5月21日
 */
public class HttpSyncClient {

    private static Logger log = LoggerFactory.getLogger(HttpSyncClient.class);

    private CloseableHttpClient httpclient;

    public final static String SAMSUNG_S4_USER_AGENT = "Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36";

    /**
     * 谷歌浏览器请求头
     */
    public final static String CHORME_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36";

    /**
     * ie11
     */
    public final static String IE11_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko";

    /**
     * 判断charset是否合理
     */
    private static final Pattern PATTERN_FOR_CHARSET = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)", Pattern.CASE_INSENSITIVE);

    /**
     * ture httpclient 自动处理302<br>
     * false 自己的程序处理302,默认
     */
    private boolean isRedirect = false;

    /**
     * post数据 使用压缩传输
     */
    private boolean isChunk = false;

    /**
     * httpclient的日志级别<br>
     * debug -1,info 0,warn 1,error 2<br>
     * 只管请求开始,结束,还有状态<br>
     */
    private int logLevel = -1;

    /**
     * 是否默认使用 keep-alive
     */
    private boolean isKeepAlive = true;

    /**
     * 是否默认使用 accept:*
     */
    private boolean isAcceptAll = true;

    /**
     * 这个参数只有在isRedirect=false时生效<br>
     * false 自己的程序处理302,默认<br>
     * true 自己处理302，非程序认
     */
    private boolean isShouDong302 = false;

    /**
     * 这个参数是为了传值，<br>
     * 有时候某些需要的字段只能在登陆的时候获得，<br>
     * 而这个字段在程序中非常有用，这时候需要放到这里<br>
     */
    private Map<String, Object> extra = new LinkedHashMap<String, Object>();

    /**
     * 全局header,优先级大于默认，小于最新设置的,不考虑key的大小写
     */
    private Map<String, String> wholeHeaders = new LinkedHashMap<String, String>();

    private BasicCookieStore cookieStore = new BasicCookieStore();

    private PoolingHttpClientConnectionManager connectionManager;

    private int connectionRequestTimeout = 3000;
    private int connectTimeout = 60000;
    private int socketTimeout = 60000;

    private MyHttpEntity lastEntity = null;

    /**
     * 代理IP
     */
    private String host;
    /**
     * 代理端口
     */
    private int port;

    int poolSize = 200;
    /**
     * 302最大跳转次数
     */
    int max302Times = 10;
    int routePoolSize = 200;
    private Map<String, String> errorExceptionMsg = new LinkedHashMap<String, String>();

    /**
     * 设置代理
     *
     * @param host
     * @param port
     */
    public void setProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 在whole_headers中设置请求头<br>
     * 优先级>默认>单次设置
     *
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        wholeHeaders.put("User-Agent", userAgent);
    }

    public void setKeepAlive(boolean isKeepAlive) {
        this.isKeepAlive = isKeepAlive;
    }

    public void setAcceptAll(boolean isAcceptAll) {
        this.isAcceptAll = isAcceptAll;
    }

    private HttpSyncClient(final Builder b) {
        logLevel = b.logLevel;
        routePoolSize = b.routePoolSize;
        poolSize = b.poolSize;
        max302Times = b.max302Times;
        String userAgent = b.userAgent;
        if (b.connectionKeeypTime < 0) {
            b.connectionKeeypTime = 5;
        }
        final int connectionKeeypTime = b.connectionKeeypTime;

        SSLConnectionSocketFactory socketFactory = b.socketFactory;
        CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
            @Override
            public CookieSpec create(HttpContext context) {

                return new BrowserCompatSpec() {
                    @Override
                    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                    }
                };
            }
        };

        if (socketFactory == null) {
            socketFactory = new HttpSSLConnectionSocketFactoryV2().getSslSocketFactory();
        }
        if (StringUtils.isBlank(userAgent)) {
            userAgent = IE11_USER_AGENT;
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 同一个路由允许最大连接数
        connectionManager.setDefaultMaxPerRoute(routePoolSize);
        connectionManager.setMaxTotal(poolSize);

        // HttpHost localhost = new HttpHost("locahost", 80);
        // 如果是多网卡，这里选择出口IP？
        // connectionManager.setMaxPerRoute(new HttpRoute(localhost), 50);

        Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create()
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                .register(CookieSpecs.BROWSER_COMPATIBILITY, new BrowserCompatSpecFactory())
                .register("easy", easySpecProvider).build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec("easy")
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build();

        ConnectionConfig connectioncfg = ConnectionConfig.custom().setCharset(Charset.forName("UTF-8")).build();
        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();

        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                String msg = exception.getMessage() == null ? "null" : exception.getMessage();
                if (exception instanceof UnknownHostException) {
                    msg = "java.net.UnknownHostException: " + msg;
                }
                if (exception instanceof ConnectException) {
                    msg = "java.net.ConnectException: " + msg;
                }
                String url = "default";
                if (context != null) {
                    HttpRequest o = (HttpRequest) context.getAttribute("http.request");
                    if (o != null) {
                        RequestLine line = o.getRequestLine();
                        if (line != null) {
                            String nurl = line.getUri();
                            if (nurl != null) {
                                url = nurl;
                            }
                        }
                    }
                }
                if (!errorExceptionMsg.containsKey(url)) {
                    errorExceptionMsg.put(url, msg);
                }
                if (executionCount >= b.retryTimes) {
                    return false;
                }

                if (exception instanceof SSLHandshakeException) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        HttpClientBuilder builder = HttpClients.custom();
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && "timeout".equalsIgnoreCase(param)) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                            log.error(ignore.getMessage());
                        }
                    }
                }
                return connectionKeeypTime * 1000;
            }
        };
        // 连接存活策略
        builder.setKeepAliveStrategy(myStrategy);

        // 链接管理器
        builder.setConnectionManager(connectionManager)
                // socket管理器
                .setDefaultSocketConfig(socketConfig)
                // 重试3次
                .setRetryHandler(myRetryHandler)
                // 链接配置，如默认字符编码
                .setDefaultConnectionConfig(connectioncfg)
                // cookie策略
                .setDefaultCookieSpecRegistry(r)
                // 浏览器请求头
                .setUserAgent(userAgent)
                // 证书
                .setSSLSocketFactory(socketFactory)
                // 链接配置，超时等
                .setDefaultRequestConfig(requestConfig)
                // cookie
                .setDefaultCookieStore(cookieStore);

        builder.addInterceptorFirst(new HttpRequestInterceptor() {

            @Override
            public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
            }
        });

        httpclient = builder.build();
    }

    public HttpSyncClient() {
        this(new HttpSyncClient.Builder());
    }

    /**
     * post方式提交json
     *
     * @param url
     * @param header
     * @param postString
     * @return
     */
    public String httpSyncPost(String url, Map<String, String> header, String postString) {

        if (header == null) {
            header = new LinkedHashMap<String, String>();
        }

        header.put("content-type", "application/json; charset=UTF-8");
        Map<String, String> postBody = new LinkedHashMap<String, String>();
        postBody.put("", postString);

        return httpSyncPost(url, header, postBody, "UTF-8");
    }

    /**
     * post提交的方法,参数位map
     *
     * @param url      提交的URL
     * @param header   请求头
     * @param postBody 请求参数
     * @param charset  字符集编码
     * @return
     * @throws Exception
     */
    public String httpSyncPost(String url, Map<String, String> header, Map<String, String> postBody, String charset) {
        MyHttpEntity text = null;
        try {
            if (logLevel <= 0) {
                log.info("begin post url :" + url);
            }
            HttpUriRequest request = getHttpUriRequest(url, "post", postBody, header);
            text = executeText(charset, header, request, text);
            if (logLevel <= 0) {
                log.info("end post url :" + url);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return text.getHtml();
    }

    /**
     * 通过httpget请求验证码
     *
     * @param url    请求的URL
     * @param header 请求头
     * @return
     * @throws IOException
     */
    public byte[] httpSyncGetBytes(String url, Map<String, String> header) {
        try {
            if (logLevel <= 0) {
                log.info("begin get url :" + url);
            }
            HttpUriRequest request = getHttpUriRequest(url, "get", null, header);
            MyHttpEntity entity = new MyHttpEntity();
            entity = executeBytes("", header, request, entity, false);
            byte[] data = entity.getBytes();
            if (logLevel <= 0) {
                log.info("end get url :" + url);
            }
            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new byte[0];
        }
    }

    /**
     * 通过httpget请求网页内容
     *
     * @param url     请求的URL
     * @param header  请求头
     * @param charset 字符集编码
     * @return
     * @throws IOException
     */
    public String httpSyncGet(String url, Map<String, String> header, String charset) {
        MyHttpEntity text = getAndEntity(url, header, charset, null);
        if (text == null || text.getHtml().length() == 0) {
            return "";
        }
        return text.getHtml();
    }

    /**
     * 通过httpget请求网页内容
     *
     * @param url 请求的URL
     * @return
     * @throws IOException
     */
    public String httpSyncGet(String url) {
        MyHttpEntity text = getAndEntity(url, null, "UTF-8", null);
        if (text == null || text.getHtml().length() == 0) {
            return "";
        }
        return text.getHtml();
    }

    //====================================================被调用方法=====================================================

    /**
     * 执行请求，返回文字
     *
     * @param charset
     * @param httpUriRequest
     * @return
     */
    private MyHttpEntity executeText(String charset, Map<String, String> header, HttpUriRequest httpUriRequest, MyHttpEntity entity) {
        if (entity == null) {
            entity = new MyHttpEntity();
        }
        entity = executeBytes(charset, header, httpUriRequest, entity, true);
        return entity;
    }

    /**
     * 执行请求，返回文字
     *
     * @param charset
     * @param httpUriRequest
     * @return
     */
    private MyHttpEntity executeBytes(String charset, Map<String, String> header, HttpUriRequest httpUriRequest, MyHttpEntity entity, boolean isHtml) {
        if (entity == null) {
            entity = new MyHttpEntity();
        }
        try {
            // 访问的URL，如果是第一次访问，那么添加
            if (entity.getUrl() == null) {
                entity.setUrl(httpUriRequest.getURI().toString());
            }
            // 最终URL，针对302，这里判断一下？
            entity.setFinalUrl(httpUriRequest.getURI().toString());

            CloseableHttpResponse httpResponse = httpclient.execute(httpUriRequest);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            Header resHeader = httpResponse.getEntity().getContentType();

            if (statusCode != HttpStatus.SC_OK) {
                if (resHeader != null) {
                    entity.setContentType(resHeader.getValue());
                    if (logLevel <= 1) {
                        log.warn("statusCode : " + statusCode + " ContentType : " + resHeader.getValue());
                    }
                } else {
                    if (logLevel <= 1) {
                        log.warn("statusCode : " + statusCode + " ContentType : unknown");
                    }
                }
            } else {
                if (resHeader != null) {
                    entity.setContentType(resHeader.getValue());
                    if (logLevel <= 0) {
                        log.info("statusCode : " + statusCode + " ContentType : " + resHeader.getValue());
                    }
                } else {
                    if (logLevel <= 0) {
                        log.info("statusCode : " + statusCode + " ContentType : unknown");
                    }
                }
            }

            // 如果httpcode等于302那么本次请求的httpcode是302
            if (entity.getTimes().getAndIncrement() == 0) {
                entity.setStatusCode(statusCode);
            }
            // 302最大跳转次数
            if (entity.getTimes().get() > max302Times) {
                log.error("跳转次数超过 {} , 设置 statusCode 为404后结束本次请求");
                statusCode = HttpStatus.SC_NOT_FOUND;
            }
            entity.setFinalStatusCode(statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                byte[] bytes = getBytes(httpResponse, entity);
                entity.setBytes(bytes);
            } else if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY || statusCode == HttpStatus.SC_MULTIPLE_CHOICES || statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {
                URL referer = httpUriRequest.getURI().toURL();
                httpUriRequest.abort();
                Header location = httpResponse.getFirstHeader("Location");
                String locationUrl = location.getValue();
                if (!locationUrl.startsWith("http")) {
                    URL u = new URL(referer, locationUrl);
                    locationUrl = u.toExternalForm();
                }
                if (isShouDong302) {
                    entity.setHtml(locationUrl);
                    lastEntity = entity;
                    return entity;
                }
                // 这里不放lastEntity = entity,因为最终出口不是这里
                return getAndEntity(locationUrl, header, charset, entity);
            } else {
                byte[] bytes = getBytes(httpResponse, entity);
                entity.setBytes(bytes);
            }
            if (isHtml) {
                if (entity.getBytes() != null && entity.getBytes().length > 0) {
                    getContent(charset, httpResponse, entity);
                    try {
                        String html = new String(entity.getBytes(), entity.getCharset());
                        entity.setHtml(html);
                    } catch (UnsupportedEncodingException e) {
                        log.error(e.getMessage());
                    }
                } else {
                    entity.setHtml("");
                }
            }
            entity.setStatus(true);
        } catch (ClientProtocolException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
            String msg = e.getMessage();
            String url = "default";
            if (httpUriRequest != null) {
                RequestLine line = httpUriRequest.getRequestLine();
                if (line != null) {
                    String nurl = line.getUri();
                    if (nurl != null) {
                        url = nurl;
                    }
                }
            }
            if (!errorExceptionMsg.containsKey(url)) {
                errorExceptionMsg.put(url, msg);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (httpUriRequest != null) {
                httpUriRequest.abort();
                connectionManager.closeExpiredConnections();
            }
        }
        lastEntity = entity;
        return entity;
    }

    private MyHttpEntity getAndEntity(String url, Map<String, String> header, String charset, MyHttpEntity entity) {
        try {
            if (logLevel <= 0) {
                log.info("begin get url :" + url);
            }
            HttpUriRequest request = getHttpUriRequest(url, "get", null, header);
            entity = executeText(charset, header, request, entity);
            if (logLevel <= 0) {
                log.info("end get url :" + url);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return entity;
    }

    /**
     * 请求转换成汉字
     *
     * @param charset
     * @param httpResponse
     * @return
     * @throws IOException
     */
    protected void getContent(String charset, HttpResponse httpResponse, final MyHttpEntity myEntity) throws IOException {
        // 没有传递编码
        if ("".equals(charset) || charset == null) {
            Header header = httpResponse.getEntity().getContentType();
            // charset
            // 1、encoding in http header Content-Type
            String ContentType = "";
            if (header != null) {
                ContentType = header.getValue();
            }

            String htmlCharset = getHtmlCharset(ContentType, myEntity.getBytes());
            if (logLevel <= -1) {
                log.debug("charset is " + htmlCharset);
            }
            if (htmlCharset != null) {
                myEntity.setCharset(htmlCharset);
            } else {
                if (logLevel <= -1) {
                    log.debug("Charset autodetect failed, use utf-8 as charset");
                }
                myEntity.setCharset("utf-8");
            }
        } else {
            // 如果已经传递编码，那么使用传递的
            myEntity.setCharset(charset);
        }
    }

    private byte[] getBytes(HttpResponse httpResponse, final MyHttpEntity myEntity) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        byte[] byteContent = null;
        if (entity.isChunked()) {
            BufferedInputStream remoteBIS = new BufferedInputStream(entity.getContent());
            ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
            int i = remoteBIS.read();
            int j = 0;
            do {
                try {
                    baos.write(i);
                    i = remoteBIS.read();
                } catch (IOException ex) {
                    log.warn("chunked");
                    break;
                }
                j++;
                if (j > 1024 * 1024 * 10) {
                    break;
                }
            } while (i != -1);
            remoteBIS.close();

            byteContent = baos.toByteArray();
            baos.close();
        } else {
            byteContent = EntityUtils.toByteArray(entity);
        }
        myEntity.setBytes(byteContent);
        return byteContent;
    }


    /**
     * 构造请求的方法，如post，get，header等<br>
     * 设置请求参数，如超时时间
     *
     * @param url      请求的URL
     * @param method   请求的方法
     * @param postBody post的数据
     * @param headers  请求头
     * @return
     */
    private HttpUriRequest getHttpUriRequest(String url, String method, Map<String, String> postBody, Map<String, String> headers) {
        RequestBuilder requestBuilder = buildRequestMethod(method, postBody, headers).setUri(url);

        Map<String, String> nowHeader = new LinkedHashMap<String, String>(wholeHeaders);

        if (isAcceptAll) {
            nowHeader.put("Accept", "*/*");
        }

        if (isKeepAlive) {
            nowHeader.put("Connection", "keep-alive");
        }

        if (headers != null && headers.size() > 0) {
            nowHeader.putAll(headers);
        }

        for (Map.Entry<String, String> headerEntry : nowHeader.entrySet()) {
            requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
        }

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setRedirectsEnabled(isRedirect)
                .setCookieSpec(CookieSpecs.BEST_MATCH);

        // 使用代理
        if (null != host && !"".equals(host) && port > 10) {
            HttpHost proxy = new HttpHost(host, port);
            requestConfigBuilder.setProxy(proxy);
        }

        requestBuilder.setConfig(requestConfigBuilder.build());
        return requestBuilder.build();
    }

    /**
     * 设置请求参数<br>
     * 如果想提交一段字符串<br>
     * 那么需要将header中的content-type设置成非application/x-www-form-urlencoded;<br>
     * 将字符串放到postdata中参数名postdata
     *
     * @param method
     * @param postdata
     * @param headers
     * @return
     */
    protected RequestBuilder buildRequestMethod(String method, Map<String, String> postdata, Map<String, String> headers) {
        if (method == null || "get".equalsIgnoreCase(method)) {
            return RequestBuilder.get();
        } else if ("post".equalsIgnoreCase(method)) {
            RequestBuilder requestBuilder = RequestBuilder.post();
            if (postdata != null) {

                String contenttype = "application/x-www-form-urlencoded; charset=UTF-8";
                /**********************************************************
                 * 先在header中取字符集<br>
                 * 如果字符集不存在，那么在contenttype中取<br>
                 * 如果还不存在使用默认
                 **********************************************************/
                String charset = "";
                if (headers != null && headers.size() > 0) {
                    charset = headers.remove("charset");// 提交数据的传输编码
                    for (String ksy : headers.keySet()) {
                        if ("Content-Type".equalsIgnoreCase(ksy)) {
                            contenttype = headers.get(ksy).toLowerCase().trim();
                            break;
                        }
                    }
                }

                // 如果字符集不存在，那么在contenttype中取
                if (null == charset || "".equals(charset) || !checkCharset(charset)) {
                    if (contenttype.contains("charset")) {
                        // 如果在请求的编码中，存在网页编码，那么提取改编码
                        String ncharset = contenttype.substring(contenttype.lastIndexOf("=") + 1, contenttype.length());
                        // 如果提取到的编码合法那么使用该编码
                        if (checkCharset(ncharset)) {
                            charset = ncharset;
                        }
                    }
                }

                // 如果还不存在使用默认
                if (null == charset || "".equals(charset) || !checkCharset(charset)) {
                    charset = "UTF-8";// 提交数据的传输编码
                }

                if ("".equals(contenttype) || contenttype.toLowerCase().contains("x-www-form-urlencoded")) {
                    List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                    for (String str : postdata.keySet()) {
                        NameValuePair n = new BasicNameValuePair(str, postdata.get(str));
                        formParams.add(n);
                    }

                    UrlEncodedFormEntity entity = null;
                    try {

                        entity = new UrlEncodedFormEntity(formParams, charset);
                        if (isChunk) {
                            entity.setChunked(true);
                        }
                    } catch (UnsupportedEncodingException e) {
                        log.error(e.getMessage(), e);
                    }
                    requestBuilder.setEntity(entity);
                } else {

                    if (logLevel <= 0) {
                        log.info("post Content-Type : [ " + contenttype + " ] , pay attention to it .");
                    }
                    String pstdata = postdata.get("postdata");// 提交的数据
                    if ("".equals(pstdata) || pstdata == null) {
                        pstdata = postdata.get("");// 提交的数据
                    }
                    if (pstdata == null) {
                        pstdata = "";
                    }
                    StringEntity entity = new StringEntity(pstdata, charset);// 解决中文乱码问题
                    entity.setContentType(contenttype);
                    if (isChunk) {
                        entity.setChunked(true);
                    }
                    requestBuilder.setEntity(entity);
                }
            } else {
                log.warn("The Method Is Post,But No Post Data .");
            }
            return requestBuilder;
        } else if ("head".equalsIgnoreCase(method)) {
            return RequestBuilder.head();
        } else if ("put".equalsIgnoreCase(method)) {
            return RequestBuilder.put();
        } else if ("delete".equalsIgnoreCase(method)) {
            return RequestBuilder.delete();
        } else if ("trace".equalsIgnoreCase(method)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    /**
     * 根据contenttype,判断网页字符集
     *
     * @param ContentType
     * @param contentBytes
     * @return
     * @throws IOException
     */
    private static String getHtmlCharset(String ContentType, byte[] contentBytes) throws IOException {

        String charset = getCharset(ContentType);
        if (charset != null && charset.length() > 0) {
            return charset;
        }
        // use default charset to decode first time
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        // 2、charset in meta
        if (content != null && content.length() > 0) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                // 2.1、html4.01 <meta http-equiv="Content-Type"
                // content="text/html; charset=UTF-8" />
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                // 2.2、html5 <meta charset="UTF-8" />
                else if (metaCharset != null && metaCharset.length() > 0) {
                    charset = metaCharset;
                    break;
                }
            }
        }
        log.debug("Auto get charset: {}" + charset);
        // 3、todo use tools as cpdetector for content decode
        return charset;
    }

    private static String getCharset(String contentType) {
        Matcher matcher = PATTERN_FOR_CHARSET.matcher(contentType);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (checkCharset(charset)) {
                return charset;
            }
        }
        return null;
    }

    /**
     * 判断字符集是否合法，jdk提供的竟然报异常。。。
     *
     * @param charset
     * @return
     */
    private static boolean checkCharset(String charset) {
        boolean b = false;
        try {
            b = Charset.isSupported(charset);
        } catch (IllegalCharsetNameException e) {
            log.error(e.getMessage());
        }

        return b;
    }


    public MyHttpEntity getBytes(String url, Map<String, String> header) {
        MyHttpEntity entity = new MyHttpEntity();
        try {
            if (logLevel <= 0) {
                log.info("begin get url :" + url);
            }
            HttpUriRequest request = getHttpUriRequest(url, "get", null, header);
            entity = executeBytes("", header, request, entity, false);
            if (logLevel <= 0) {
                log.info("end get url :" + url);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            entity.setBytes(new byte[0]);
        }
        return entity;
    }

    public MyHttpEntity httpSyncPostBytes(String url, Map<String, String> header, Map<String, String> postdata) {
        MyHttpEntity entity = new MyHttpEntity();
        try {
            if (logLevel <= 0) {
                log.info("begin post url :" + url);
            }
            HttpUriRequest request = getHttpUriRequest(url, "post", postdata, header);
            entity = executeBytes("", header, request, entity, false);
            if (logLevel <= 0) {
                log.info("end post url :" + url);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            entity.setBytes(new byte[0]);
        }
        return entity;
    }

    public MyHttpEntity getBytes(String url) {
        return getBytes(url, null);
    }

    /**
     * 通过httpget请求网页内容
     *
     * @param url     请求的URL
     * @param header  请求头
     * @param charset 字符集编码
     * @return
     * @throws IOException
     */
    public MyHttpEntity get(String url, Map<String, String> header, String charset) {
        MyHttpEntity text = getAndEntity(url, header, charset, null);
        return text;
    }

    public void addCookie(Cookie cookie) {
        this.getCookieStore().addCookie(cookie);
    }

    /**
     * 通过get请求页面内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public MyHttpEntity get(String url) {
        return get(url, null, "");
    }

    /**
     * 获得cookiestore
     *
     * @return
     */
    public CookieStore getCookieStore() {
        return cookieStore;
    }

    /**
     * 使用结束要关闭httpclient
     */
    public void close() {
        try {
            // connectionManager.shutdown();
            httpclient.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static Map<String, String> doHeader(String borrowHeader) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        if (borrowHeader == null || "".equals(borrowHeader) || !borrowHeader.contains(":")) {
            return header;
        }
        String[] h = borrowHeader.split(":", 2);
        header.put(h[0], h[1]);
        return header;
    }

    /**
     * 将cookiestore转换成string<br>
     * 如果为空，使用默认对象
     *
     * @param cookieStore
     * @return
     */
    public String CookieStore2String(CookieStore cookieStore) {
        if (cookieStore == null) {
            cookieStore = this.getCookieStore();
        }
        StringBuffer sb = new StringBuffer();
        for (Cookie cookie : cookieStore.getCookies()) {
            sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
        }
        String cookie = sb.toString();
        if (cookie.length() > 1) {
            cookie = cookie.substring(0, cookie.length() - 1);
        }
        return cookie;
    }

    public void addCookie(String name, String value, String domain, Date expiry, String path) {
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        if (domain != null && !"".equals(domain)) {
            cookie.setDomain(domain);
        }
        if (expiry != null) {
            cookie.setExpiryDate(expiry);
        }
        if (path != null && !"".equals(path)) {
            cookie.setPath(path);
        }

        this.getCookieStore().addCookie(cookie);
    }

    /**
     * 这个参数是为了传值，<br>
     * 有时候某些需要的字段只能在登陆的时候获得，<br>
     * 而这个字段在程序中非常有用，这时候需要放到这里<br>
     * 这个方法永远不会null
     *
     * @return
     */
    public Map<String, Object> getExtra() {
        if (extra == null) {
            extra = new LinkedHashMap<String, Object>();
        }
        return extra;
    }

    /**
     * 这个参数是为了传值，<br>
     * 有时候某些需要的字段只能在登陆的时候获得，<br>
     * 而这个字段在程序中非常有用，这时候需要放到这里<br>
     *
     * @param key
     * @param value
     */
    public void putExtra(String key, Object value) {
        getExtra().put(key, value);
    }

    /**
     * 自己处理302，非程序
     *
     * @param isShouDong302
     */
    public void setShouDong302(boolean isShouDong302) {
        this.isShouDong302 = isShouDong302;
    }

    public String getCookieValue(String key) {
        CookieStore cookieStore = this.getCookieStore();
        for (Cookie cookie : cookieStore.getCookies()) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public CloseableHttpClient getHttpclient() {
        return httpclient;
    }

    /**
     * 全局header,优先级大于默认，小于最新设置的,不考虑key的大小写
     *
     * @param key
     * @param value
     */
    public void putWholeHeader(String key, String value) {
        if (wholeHeaders == null) {
            wholeHeaders = new LinkedHashMap<String, String>();
        }
        if (StringUtils.isEmpty(key)) {
            key = "";
        }
        if (StringUtils.isEmpty(value)) {
            value = "";
        }
        wholeHeaders.put(key, value);
    }

    public void setChunk(boolean isChunk) {
        this.isChunk = isChunk;
    }

    public Map<String, String> getErrorExceptionMsg() {
        return errorExceptionMsg;
    }

    public static class Builder {

        /**
         * 日志级别,debug -1,info 0,warn 1,error 2
         */
        private int logLevel = -1;

        private SSLConnectionSocketFactory socketFactory;
        private int poolSize = 200;
        private int routePoolSize = 200;
        /**
         * 302最大跳转次数
         */
        private int max302Times = 10;
        /**
         * 最大重试次数
         */
        private int retryTimes = 3;
        private String userAgent;
        private int connectionKeeypTime = -1;

        /**
         * 日志级别,debug -1,info 0,warn 1,error 2
         *
         * @param logLevel
         * @return
         */
        public Builder setLogLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder setSocketFactory(SSLConnectionSocketFactory socketFactory) {
            this.socketFactory = socketFactory;
            return this;
        }

        public Builder setPoolSize(int poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public Builder setRoutePoolSize(int routePoolSize) {
            this.routePoolSize = routePoolSize;
            return this;
        }

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder setConnectionKeeypTime(int connectionKeeypTime) {
            log.info("connectionKeeypTime : {}", connectionKeeypTime);
            if (connectionKeeypTime > -1 && connectionKeeypTime < 10000) {
                this.connectionKeeypTime = connectionKeeypTime;
            } else {
                log.warn("connectionKeeypTime 不合法 : {}", connectionKeeypTime);
            }
            return this;
        }

        public Builder setRetryTimes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public HttpSyncClient build() {
            return new HttpSyncClient(this);
        }

    }

    /**
     * 连接池获取连接的timeout
     *
     * @param connectionRequestTimeout
     */
    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    /**
     * 指客户端和服务器建立连接的timeout<br>
     * ConnectionTimeOutException<br>
     * 超时后会ConnectionTimeOutException
     *
     * @param connectTimeout
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * 客户端从服务器读取数据的timeout，超出后会抛出SocketTimeOutException
     *
     * @param socketTimeout
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public static Builder createBuilder() {
        return new HttpSyncClient.Builder();
    }

    /**
     * 获取设置的代理
     *
     * @return
     */
    public String getProxy() {
        if (StringUtils.isNotBlank(host) && port > 0) {
            return host + ":" + port;
        }
        return null;
    }

    public MyHttpEntity getLastEntity() {
        return lastEntity;
    }

}