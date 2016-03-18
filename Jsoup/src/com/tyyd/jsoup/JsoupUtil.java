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
			Document document = Jsoup.connect("http://top.hongxiu.com/yuepiaoy1.html").get();
			Elements ul = document.select("div#lbox").select("ul");
			for (Element element : ul) {
				Elements li = element.select("li");
				for (Element element2 : li) {
					System.out.println(element2.text());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
