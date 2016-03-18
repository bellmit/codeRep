package netease.xiupin.liantiao;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/**
 * @author hzzhuhao1
 *  2016年3月18日
 */
public class XiupinTest {
	
	public static void main(String[] args) {
//		String url = "http://pis.paopao.163.com/order/koala/synStock";
		String url = "http://pis.paopao.163.com/order/koala/cancel";
		
		Map<String, String> params = new HashMap<>();
//		params.put("sku_id", "278090");
//		params.put("count", String.valueOf(1));
//		params.put("type", "0");
		
		params.put("order_id", "2016030519063211187106979");
		params.put("reason", "dev test");
		
		String resp,message;
		Boolean result;
		int errorCode;
		resp = "";
		long start = System.currentTimeMillis();
		try {
			resp = WebUtils.doPost(url, params, 5 * 60 * 1000, 5 * 60 * 1000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("resp:" + resp);
//		resp = "{\"success\":\"true\",\"errorCode\":\"200\",\"message\":\"ok\"}";
		JSONObject object = JSONObject.parseObject(resp);
		result = (Boolean) object.get("success");
		errorCode = (Integer) object.get("errorCode");
		message = (String) object.get("message");
		System.out.println(result + ":" + errorCode + ":" + message);
		System.out.println("time:" + (System.currentTimeMillis()-start));
		
	}
}
