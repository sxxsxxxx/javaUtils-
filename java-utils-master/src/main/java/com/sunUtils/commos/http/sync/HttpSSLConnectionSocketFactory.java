package com.sunUtils.commos.http.sync;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * apache httpclient https问题，需要设置SSLConnectionSocketFactory 解决https问题
 * 
 * @author dzp
 *
 */
public class HttpSSLConnectionSocketFactory {

	public static Logger logger = LoggerFactory.getLogger(HttpSSLConnectionSocketFactory.class);
	public static SSLConnectionSocketFactory sslSocketFactory;
	static {
		SSLContextBuilder sSLContextBuilder = SSLContexts.custom();
		try {
			sSLContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			});
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (KeyStoreException e) {
			logger.error(e.getMessage());
		}
		SSLContext sslContext;
		try {
			sslContext = sSLContextBuilder.build();
			sslSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		} catch (KeyManagementException e) {
			logger.error(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}
	}
}