package com.hx.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SendRequest {
	public static Response request(MyRequest req) {
		//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
		Response resp = null;
		try {
			Connection conn = Jsoup.connect(req.getUrl());//.proxy(proxy);
			conn.userAgent(req.getUA());
			for (Entry<String, String> e : req.getHeaders().entrySet()) {
				conn.header(e.getKey(), e.getValue());
			}
			for (Entry<String, String> e : req.getDatas().entrySet()) {
				conn.data(e.getKey(), e.getValue());
			}
			conn.cookies(req.getCookies());
			Document doc = null;
			if (req.getReqType().equalsIgnoreCase("get")) {
				doc = conn.get();
			} else {
				doc = conn.post();
			}
			resp = conn.response();
			return resp;
		} catch (HttpStatusException e1) {
			System.out.println(e1.getStatusCode() + ":" + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
