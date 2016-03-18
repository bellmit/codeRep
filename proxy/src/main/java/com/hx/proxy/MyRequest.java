package com.hx.proxy;

import java.util.HashMap;
import java.util.Map;

public class MyRequest {
	public static final String separator="\n";
	private String url;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> datas = new HashMap<String, String>();
	private Map<String, String> cookies = new HashMap<String, String>();
	private String UA="Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-S5660 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 MicroMessenger/4.5.255";
	private String reqType = "get";

	public MyRequest(String reqType) {
		this.reqType = reqType;
	}

	public MyRequest() {

	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public Map<String, String> getDatas() {
		return datas;
	}
	public void setDatas(Map<String, String> datas) {
		this.datas = datas;
	}
	public Map<String, String> getCookies() {
		return cookies;
	}
	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}
	public String getUA() {
		return UA;
	}
	public void setUA(String uA) {
		UA = uA;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

}
