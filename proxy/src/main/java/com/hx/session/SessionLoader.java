package com.hx.session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.hx.proxy.MyRequest;
import com.hx.proxy.SendRequest;
import com.hx.proxy.StoreRequest;
import com.hx.util.Tools;

/**
 * 读取session文件
 * 
 * @author hx
 *
 */
public class SessionLoader {
	private static Logger logger = Logger.getLogger(SessionLoader.class);
	private static LinkedBlockingQueue<Integer> flagQueue = new LinkedBlockingQueue<Integer>(2);
	private static String rootDir = "d:/";
	private static int lineNum = 13;
	private static int maxThreadNum = 4000;// 线程总数（开4000个线程，抓8000以上的包）
	private static String startTime = "2015-12-11 10:59:58";
	private static int packageNum = 0;

	public static void prepareSession() {
		String os = System.getProperty("os.name");
		if (os != null && os.startsWith("Windows")) {
			rootDir = "d:/";
		} else {
			rootDir = "/root/";
		}
		File file = new File(rootDir + "cmb.txt");
		BufferedReader reader = null;
		try {
			logger.info("开始读取请求包数据 ...");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if (tempString.contains("mlife.cmbchina.com/Campaign/panicBuyV2.json")) {
					StringBuilder sb = new StringBuilder();
					sb.append(tempString).append("\r\n");
					for (int i = 1; i <= lineNum - 1; i++) {
						tempString = reader.readLine();
						if (tempString.length() > 0) {
							sb.append(tempString).append("\r\n");
						}
					}
					int ret = StoreRequest.parseForCMB(sb.toString());
					packageNum = ret;
					// logger.info("total requests = " + ret);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {
		LogLog.setInternalDebugging(true); 
		if (args != null && args.length == 1) {
			// maxThreadNum = Integer.parseInt(args[0]);
		}
		setFlagThread();
		SessionLoader.prepareSession();
		logger.info("招行当前时间：" + getServerTime());
		logger.info("本机当前时间：" + Tools.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
		logger.info("抢兑开始时间：" + startTime);
		logger.info("启动线程数：" + maxThreadNum);
		logger.info("总包数：" + packageNum);

		for (int i = 0; i < maxThreadNum; i++) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					while (true) {
						MyRequest req;
						try {
							req = StoreRequest.queue.poll();
							if (req == null) {
								break;
							}
							long st = System.currentTimeMillis();
							Integer count = flagQueue.take();// 获取允许启动的标记
							StringBuilder sb = new StringBuilder("第" + count + "次" + " -> " + Tools.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
							Response resp = SendRequest.request(req);
							sb.append(" -> ").append(resp.body()).append(" -> ").append(System.currentTimeMillis() - st);
							logger.info(sb);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			t.start();
		}
		// 主线程休息，让子线程有充足的时间运行
		Thread.sleep(1200000);
	}

	/**
	 * 获取招行当前时间
	 * 
	 * @return
	 */
	private static String getServerTime() {
		Connection conn = Jsoup
				.connect("http://mlife.cmbchina.com/Campaign/getPanicBuyCampaignList.json?p0=a&p1=33&p2=a71e694afa574a239c2bb39be627f23f&p3=0c7244b624ae480387a4e825a3643ad38&p4=73ac7f94e41544489384f6408156915e&p5=5da29832f2ca43c6afb0ebb8abf535e8&p6=474673245&p7=19499afff8a24c879dd43325cdc2ec19&p8=013c55f84358410e937bbafcbb5ad7c3&p9=null&p10=2491d089b23f4b58b7bb56b51fcf68b6");
		conn.userAgent("Dalvik/1.6.0 (Linux; U; Android 4.3; Coolpad 9190L Build/JLS36C);(cmblife 5.0.3/33)");
		conn.header("X-Online-Host", "mlife.cmbchina.com");
		conn.header("Accept-Language", "zh-CN");
		conn.header("Charset", "UTF-8");
		conn.header("Connection", "Keep-Alive");
		conn.header("Accept", "*/*");
		conn.header("Content-Type", "application/x-www-form-urlencoded");
		conn.header("Host", "mlife.cmbchina.com");
		conn.header("Accept-Encoding", "gzip");
		conn.header("Content-Length", "69");

		conn.data("pageSize", "999");
		conn.data("campaignNo", "");
		conn.data("cityId", "571");
		conn.data("pageIndex", "1");
		conn.data("cGName", "TYZZYQFP201510");

		Document doc = null;
		try {
			doc = conn.post();
			Pattern pattern = Pattern.compile(",\"sysCurTime\":\"(.*)\"}}");
			Matcher matcher = pattern.matcher(doc.text());
			while (matcher.find()) {
				return matcher.group(1);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 每隔1毫秒，往开关池里丢2个启动信号
	 */
	private static void setFlagThread() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				long stratTime = Tools.str2Date(startTime).getTime();
				while (System.currentTimeMillis() < stratTime) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}

				int count = 1;
				while (true) {
					try {
						Thread.sleep(1);
						flagQueue.put(count);
						count++;
						flagQueue.put(count);
						count++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
}
