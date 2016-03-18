package com.hx.proxy.thread;

import org.jsoup.Connection.Response;

import com.hx.proxy.MyRequest;
import com.hx.proxy.SendRequest;
import com.hx.proxy.StoreRequest;

public class RequestThread implements Runnable {
	private MyRequest req = null;
	public void run() {
		while (true) {
			getReq();
			Response resp = SendRequest.request(req);
			System.out.println(resp.body());
		}

	}

	private void getReq() {
		try {
			req = StoreRequest.queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
