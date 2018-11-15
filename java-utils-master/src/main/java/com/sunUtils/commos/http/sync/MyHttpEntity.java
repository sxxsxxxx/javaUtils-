package com.sunUtils.commos.http.sync;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.atomic.AtomicInteger;

public class MyHttpEntity {

	private boolean status = false;
	/**
	 * 网页的Content-Type
	 */
	private String contentType;

	private AtomicInteger times = new AtomicInteger(0);
	/**
	 * 网页的编码
	 */
	private String charset;
	/**
	 * 网页返回状态
	 */
	private int statusCode = 404;

	private int finalStatusCode;

	/**
	 * URL
	 */
	private String url;

	/**
	 * 最终URL，针对302
	 */
	private String finalUrl;

	/**
	 * 网页最后更新时间
	 */
	private long lastmodify = 0;

	private byte[] bytes;

	/**
	 * 返回的网页
	 */
	private String html = "";

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFinalUrl() {
		return finalUrl;
	}

	public void setFinalUrl(String finalUrl) {
		this.finalUrl = finalUrl;
	}

	public long getLastmodify() {
		return lastmodify;
	}

	public void setLastmodify(long lastmodify) {
		this.lastmodify = lastmodify;
	}

	public String getHtml() {
		return html == null ? "" : html;
	}

	public void setHtml(String html) {
		this.html = html;
	}


	public AtomicInteger getTimes() {
		return times;
	}

	public void setTimes(AtomicInteger times) {
		this.times = times;
	}

	public int getFinalStatusCode() {
		return finalStatusCode;
	}

	public void setFinalStatusCode(int finalStatusCode) {
		this.finalStatusCode = finalStatusCode;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
