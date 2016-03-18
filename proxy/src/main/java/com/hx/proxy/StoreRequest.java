package com.hx.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.hx.util.Tools;

public class StoreRequest {
	// 保存客户端发送的请求
	public static LinkedBlockingQueue<MyRequest> queue = new LinkedBlockingQueue<MyRequest>();

	/**
	 * 解析招行的请求
	 * 
	 * @param content
	 * @return
	 */
	public static int parseForCMB(String contents) {
		if (Tools.isEmpty(contents)) {
			return 0;
		}
		MyRequest req = new MyRequest();
		String[] strs = contents.split(MyRequest.separator);
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> datas = new HashMap<String, String>();
		Map<String, String> cookies = new HashMap<String, String>();
		req.setHeaders(headers);
		req.setDatas(datas);
		req.setCookies(cookies);
		for (String s : strs) {
			if (Tools.isEmpty(s)) {
				continue;
			}
			if (s.startsWith("POST http") || s.startsWith("GET http")) {
				if(s.startsWith("POST")){
					req.setReqType("POST");
				}else{
					req.setReqType("G");
				}
				req.setUrl(s.split(" ")[1]);
			} else if (s.startsWith("Cookie:")) {
				String cookie = s.substring(s.indexOf(":"), s.length());
				for (String c : cookie.split(";")) {
					cookies.put(c.split("=")[0], c.split("=")[1]);
				}
			} else if (s.indexOf(":") > 0) {
				int index = s.indexOf(":");
				if (s.substring(0, index).trim().equalsIgnoreCase("User-Agent")) {
					req.setUA(s.substring(index + 1, s.length()).trim());
				}
				headers.put(s.substring(0, index).trim(), s.substring(index + 1, s.length()).trim());
			} else {
				String[] ds = s.split("&");
				for (String s1 : ds) {
					datas.put(s1.split("=")[0], s1.split("=")[1].replaceFirst("\\r", ""));
				}
			}
		}

		try {
			queue.put(req);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return queue.size();
	}
}
