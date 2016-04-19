package com.tyyd.jsoup;

import java.io.IOException;





import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年7月14日下午4:34:23
 * @version 1.0
 */
public class JsoupUtil {
	public static void main(String[] args) {
		try {
			for(int i = 294800;i<295000;i++) {
				String url = "http://www.kaola.com/product/" + i + ".html";
				Document document = Jsoup.connect(url).get();
				Elements ul = document.select("article.mainWrap").select("div.PInfoWrap.clearfix").
						select("dl.PInfo.PInfo-standout").select("dt.product-title");
				if(ul.text().length() > 0) {
					System.out.println(i + ":" + new String(ul.text().getBytes(),"GBK").replace("?", " "));
				}
			}
//			for (Element element : ul) {
//				Elements li = element.select("li");
//				for (Element element2 : li) {
//					System.out.println(element2.text());
//				}
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
