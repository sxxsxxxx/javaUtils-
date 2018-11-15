package com.sunUtils.commos.http.sync;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 <tr>
 * Engine Class Implemented &nbsp;&nbsp;&nbsp; Algorithm or Protocol <br>
 * KeyStore &nbsp;&nbsp;&nbsp; PKCS12 <br>
 * KeyManagerFactory &nbsp;&nbsp;&nbsp; PKIX, SunX509 <br>
 * TrustManagerFactory &nbsp;&nbsp;&nbsp; PKIX (a.k.a. X509 or SunPKIX), SunX509
 * <br>
 * SSLContext &nbsp;&nbsp;&nbsp; SSLv3 (a.k.a. SSL), TLSv1 (a.k.a. TLS),
 * TLSv1.1, TLSv1.2 <br>
 * 关于更多https的知识，参考这篇文章 <a href=
 * 'http://docs.oracle.com/javase/7/docs/technotes/guides/security/jsse/JSSERefG
 * u i d e . h t m l # A p p A ' > Java Secure Socket Extension (JSSE) Reference
 * Guide </a>(java安全套接字扩展（JSSE）参考指南)
 * 
 * 
 * @author xuyiming
 *
 */
public class HttpSSLConnectionSocketFactoryV2 {

	public static Logger logger = LoggerFactory.getLogger(HttpSSLConnectionSocketFactoryV2.class);
	private SSLConnectionSocketFactory sslSocketFactory;
	private static Map<String, String> protocolMap = new LinkedHashMap<String, String>();
	private static Map<String, SSLContextBuilder> builderMap = new LinkedHashMap<String, SSLContextBuilder>();
	private static Map<String, Map<String, Object>> certMap = new LinkedHashMap<String, Map<String, Object>>();
	private static Set<String> protocolSet = new LinkedHashSet<String>();
	private static Map<String, Boolean> notUseTrustStrategy = new LinkedHashMap<String, Boolean>();
	static {
		protocolSet.add("SSLv3");// 等于SSL
		protocolSet.add("SSL");
		protocolSet.add("TLSv1");// 等于TLS
		protocolSet.add("TLS");
		protocolSet.add("TLSv1.1");
		protocolSet.add("TLSv1.2");
	}

	/**
	 * 设置https证书，只能访问单一网站，工商有问题
	 * 
	 * @param path
	 * @param password
	 */
	public static void setSSLCert(String path, String password) {
		if (StringUtils.isNotBlank(path) && new File(path).exists()) {
			Map<String, Object> cMap = new LinkedHashMap<String, Object>();
			cMap.put("path", new File(path));
			cMap.put("password", password);
			certMap.put(Thread.currentThread().getName(), cMap);
		} else {
			logger.error("你想设置的证书不存在 {}", path);
			return;
		}
	}

	/**
	 * 不使用证书
	 * 
	 * @param notUseTrust
	 */
	public static void notUseTrustStrategy(boolean notUseTrust) {
		notUseTrustStrategy.put(Thread.currentThread().getName(), notUseTrust);
	}

	/**
	 * 设置协议
	 * 
	 * @param protocol
	 */
	public static void setProtocol(String protocol) {
		if (!protocolSet.contains(protocol)) {
			logger.error("你想设置的协议不存在 {} 支持的协议 {}", protocol, protocolSet);
			return;
		}
		protocolMap.put(Thread.currentThread().getName(), protocol);
	}

	/**
	 * 直接传递sslContext<br>
	 * https://fund.hrbgjj.org.cn:8443/fund/jsp/search_manage/webFundLogin.jsp<br>
	 * https://epass.icbc.com.cn/servlet/ICBCVerificationCodeImageCreate?
	 * randomId =1&height=36&width=90&appendRandom=1<br>
	 * 
	 * @param builder
	 */
	public static void setSSLContextBuilder(SSLContextBuilder builder) {
		builderMap.put(Thread.currentThread().getName(), builder);
	}

	public HttpSSLConnectionSocketFactoryV2() {
		String name = Thread.currentThread().getName();
		SSLContextBuilder sSLContextBuilder = builderMap.get(name);
		if (sSLContextBuilder == null) {
			sSLContextBuilder = SSLContexts.custom();
			Boolean notUse = notUseTrustStrategy.remove(name);
			if (notUse == null || !notUse) {
				try {
					Map<String, Object> cMap = certMap.remove(name);
					TrustStrategy t = new TrustStrategy() {
						@Override
						public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
							return true;
						}
					};
					if (cMap != null) {
						File path = (File) cMap.get("path");
						String password = (String) cMap.get("password");
						sSLContextBuilder.loadTrustMaterial(path, password.toCharArray(), t);
					} else {
						sSLContextBuilder.loadTrustMaterial(null, t);
					}
					String protocol = protocolMap.remove(name);
					if (!StringUtils.isBlank(protocol)) {
						// http://docs.oracle.com/javase/7/docs/technotes/guides/security/jsse/JSSERefGuide.html#AppA
						logger.info("connect https use " + protocol);
						sSLContextBuilder.useProtocol(protocol);
					}
				} catch (NoSuchAlgorithmException e) {
					logger.error(e.getMessage(), e);
				} catch (KeyStoreException e) {
					logger.error(e.getMessage(), e);
				} catch (CertificateException e) {
					logger.error(e.getMessage(), e);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		SSLContext sslContext;
		try {
			sslContext = sSLContextBuilder.build();
			sslSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		} catch (KeyManagementException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public SSLConnectionSocketFactory getSslSocketFactory() {
		return sslSocketFactory;
	}

}