package com.hx.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	private static Random random = new Random(System.currentTimeMillis());
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	public static boolean isEmpty(Object obj) {
		return obj == null ? true : isEmpty(obj.toString());
	}

	/**
	 * 检测数组是否为空
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean notEmpty(Object[] arr) {
		return arr != null && arr.length > 0;
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		if (date == null) {
			return "";
		}
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (date == null) {
			return null;
		}
		return str2Date(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @Description: 按照参数format返回日期
	 * @author: hx Create at: 2011-12-9 下午02:00:02
	 * @param: @param date
	 * @param: @param format
	 * @param: @return
	 * @return: Date
	 */
	public static Date str2Date(String date, String format) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * @Title: removeDuplicateWithOrder
	 * @Description: 去list中除重复的值
	 * @author: wolf Create at: 2012-12-18 下午3:11:05
	 * @param: @param list
	 * @param: @return
	 * 
	 * @return: List<?>
	 */
	public static List removeDuplicateWithOrder(List list) {
		Set<Object> set = new HashSet<Object>();
		List<Object> newList = new ArrayList<Object>();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		return newList;
	}

	/**
	 * @Title: rand
	 * @Description: 获得一个时间+随机值的随机数
	 * @author: wolf Create at: 2013-1-22 下午8:52:23
	 * @param: @return
	 * 
	 * @return: String
	 */
	public static String rand() {
		String s = System.currentTimeMillis() + random.nextInt(99999) + "";
		return s;
	}

	public static String todayStr() {
		return date2Str(new Date(), "yyyyMMdd");
	}

	public static String yearStr() {
		return date2Str(new Date(), "yyyy");
	}

	public static String monthStr() {
		return date2Str(new Date(), "MM");
	}

	public static String dayStr() {
		return date2Str(new Date(), "dd");
	}

	public static String yearStr(Date date) {
		return date2Str(date, "yyyy");
	}

	public static String monthStr(Date date) {
		return date2Str(date, "MM");
	}

	public static String dayStr(Date date) {
		return date2Str(date, "dd");
	}

	public static Date yesterday() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	public static Date yesterday(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	/**
	 * 计算日期的加减后的日期,
	 * 
	 * @param date
	 * @param offset
	 *            正数表示往后推
	 * @return 返回date yyyyMMdd 000000
	 */
	public static Date calcDate(Date date, int offsetDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(calendar.DATE, offsetDay);
		return calendar.getTime();
	}

	/**
	 * 计算日期的加减后的日期
	 * 
	 * @param date
	 * @param offset
	 * @return 返回date yyyyMMdd HHmmss
	 */
	public static Date calcDateTime(Date date, int offsetDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, offsetDay);
		return calendar.getTime();
	}

	/**
	 * @Title: randStr
	 * @Description: 返回时间戳+随机4位数
	 * @author: wolf Create at: 2013-2-6 上午10:43:01
	 * @param: @return
	 * 
	 * @return: String
	 */
	public static String randStr() {
		int r = random.nextInt(10000);
		return System.currentTimeMillis() + "" + String.format("%04d", r);
	}

	/**
	 * @Title: fileCopy
	 * @Description: 文件拷贝
	 * @author: wolf Create at: 2013-2-6 上午11:04:48
	 * @param: @param f1 源
	 * @param: @param f2 目标
	 * @param: @return
	 * 
	 * @return: boolean
	 */
	public static boolean fileCopy(File f1, File f2) {
		boolean r = true;
		int length = (int) f1.length();
		FileInputStream in = null;
		FileOutputStream out = null;
		FileChannel inC = null;
		FileChannel outC = null;
		// 创建文件路径
		if (!f2.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			if (!f2.getParentFile().mkdirs()) {
				return false;
			}
		}

		try {
			in = new FileInputStream(f1);
			out = new FileOutputStream(f2);
			inC = in.getChannel();
			outC = out.getChannel();
			ByteBuffer b = null;
			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					break;
				}
				if ((inC.size() - inC.position()) < length) {
					length = (int) (inC.size() - inC.position());
				} else
					length = (int) f1.length();
				b = ByteBuffer.allocateDirect(length);
				inC.read(b);
				b.flip();
				outC.write(b);
				outC.force(false);
			}
		} catch (Exception e) {
			r = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (inC != null) {
					inC.close();
				}
				if (in != null) {
					in.close();
				}
				if (outC != null) {
					outC.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	/*
	 * Java文件操作 获取文件扩展名
	 * 
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 * 
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	public static String arr2Str(String[] strs) {
		if (strs == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (String s : strs) {
			if (sb.length() > 0) {
				sb.append(",");
			} else {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: msToStrDate
	 * @Description: 将流程执行消耗的时间转换为：XX天XX小时XX分XX秒 格式
	 * @author: Administrator Create at: 2011-12-16 下午02:46:00
	 * @param: @param timespand 毫秒级的时间间隔
	 * @param: @return
	 * 
	 * @return: String
	 */
	public static String msToStrDate(Long timespand) {
		if (timespand == null && timespand == 0) {
			return "";
		}
		long day = 0;// 天数
		long hour = 0;// 小时
		long min = 0;// 分
		long second = 0;// 秒
		StringBuilder ret = null;
		if (timespand == null || timespand == 0l || timespand < 1000l) {
			return "<1分";
		} else {
			day = timespand / (24 * 60 * 60 * 1000);
			hour = (timespand / (60 * 60 * 1000) - day * 24);
			min = ((timespand / (60 * 1000)) - day * 24 * 60 - hour * 60);
			second = (timespand / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

			ret = new StringBuilder();
			if (day != 0) {
				ret.append(day);
				ret.append("天");
			}
			if (hour != 0) {
				ret.append(hour);
				ret.append("小时");
			}
			if (min != 0) {
				ret.append(min);
				ret.append("分");
			}
			if (second != 0) {
				ret.append(second);
				ret.append("秒");
			}
			return ret.toString();
		}
	}

	public static String[] set2StrArray(Set<String> setObj) {
		if (setObj != null) {
			int len = setObj.size();
			String[] arr = new String[len];
			for (String o : setObj) {
				arr[--len] = o;
			}
			return arr;
		}
		return null;
	}

	public static Set<String> str2set(String str) {
		Set<String> ret = new HashSet<String>();
		if (Tools.isEmpty(str)) {
			return ret;
		} else {
			String[] strs = str.split(",");
			for (String s : strs) {
				ret.add(s);
			}
			return ret;
		}
	}

	public static String set2str(Set<String> setObj) {
		return set2str(setObj, "");
	}

	/**
	 * @param setObj
	 * @param quote
	 *            字符串外面包符号
	 * @return
	 */
	public static String set2str(Set<String> setObj, String quote) {
		StringBuilder sb = new StringBuilder();
		if (setObj != null) {
			for (String o : setObj) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(quote + o + quote);
			}
			return sb.toString();
		}
		return null;
	}

	public static Map<String, String> ObjMap2StrMap(Map<String, Object> map) {
		Map<String, String> newMap = new HashMap<String, String>();
		Set<Map.Entry<String, Object>> set = map.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			newMap.put(entry.getKey(), entry.getValue().toString());
		}
		return newMap;
	}

	public static boolean regMatcher(String str, String regEx) {
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		return mat.find();
	}

	public static String[] list2Arr(List<String> listStr) {
		return set2StrArray(list2Set(listStr));
	}

	public static Set<String> list2Set(List<String> listStr) {
		Set<String> setTmp = new HashSet<String>();
		for (String s : listStr) {
			setTmp.add(s);
		}
		return setTmp;
	}

	public static String list2Str(List<String> listStr) {
		StringBuilder sb = new StringBuilder();
		for (String s : listStr) {
			sb.append(s).append("\n");
		}
		return sb.toString();
	}

	public static boolean isNum(String str) {
		if (str == null) {
			return false;
		}
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public static String formatCurrency(Object o) {
		if (Tools.isEmpty(o)) {
			return "";
		}
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		if (o instanceof String) {
			return currencyFormat.format(Double.parseDouble(o.toString()));
		}
		return currencyFormat.format(o);
	}

	public static String formatNumber(Object o) {
		if (Tools.isEmpty(o)) {
			return "";
		}
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		return numberFormat.format(o);
	}

	/**
	 * @param o
	 * @param dec
	 *            要保留的小数位数
	 * @return
	 */
	public static String formatNumber(Object o, int dec) {
		if (Tools.isEmpty(o)) {
			return "";
		}
		String decF = "";
		for (int i = 0; i < dec; i++) {
			if (decF.equals("")) {
				decF += ".";
			}
			decF += "0";
		}
		DecimalFormat df1 = new DecimalFormat("0" + decF);
		if (o instanceof String) {
			return df1.format(Double.parseDouble(o.toString()));
		}
		return df1.format(o);
	}

	public static void main(String[] args) throws IOException {
		Date d=new Date();
		setSysTime(d);
	}
	/**
	 * 过滤全角
	 * 
	 * @param obj
	 * @return
	 */
	public static String filterSBC(Object obj) {
		if (Tools.isEmpty(obj)) {
			return "";
		} else {
			return filterSBC(obj.toString().trim());
		}
	}

	/**
	 * 过滤全角
	 * 
	 * @param str
	 * @return
	 */
	public static String filterSBC(String str) {
		if (Tools.isEmpty(str)) {
			return "";
		}
		str = str.replaceAll("－", "-");
		str = str.replaceAll("，", ",");
		str = str.replaceAll("：", ":");
		str = str.replaceAll("；", ";");
		str = str.trim();
		return str;
	}

	/**
	 * 获取本机的所以ip地址
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static Set<String> getRealIp() throws SocketException {
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		Set<String> ips = new HashSet<String>();
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (ip.getHostAddress() != null && !"".endsWith(ip.getHostAddress())) {
					ips.add(ip.getHostAddress());
				}
			}
		}
		return ips;
	}

	/**
	 * 获取操作系统类型
	 * 
	 * @return
	 */
	public static String getSysType() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name").toLowerCase();
		return os;
	}

	private static String percentEncode(String value) throws UnsupportedEncodingException {
		// 使用URLEncoder.encode编码后，将"+","*","%7E"做替换即满足ECS API规定的编码规范
		return value != null ? URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
	}

	public static String map2String(Map<String, String> map) {
		// 将参数Key按字典顺序排序
		String[] sortedKeys = map.keySet().toArray(new String[]{});
		Arrays.sort(sortedKeys);

		// 生成规范化请求字符串
		StringBuilder canonicalizedQueryString = new StringBuilder();
		for (String key : sortedKeys) {
			try {
				canonicalizedQueryString.append("&").append(percentEncode(key)).append("=").append(percentEncode(map.get(key)));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}
		return canonicalizedQueryString.toString();
	}

	public static Map<String, String> copyMap(Map<String, String> oldMap) {
		Map newMap = new HashMap();
		Set<String> key = oldMap.keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			newMap.put(s, oldMap.get(s));
		}
		return newMap;
	}

	public static String map2StringReq(Map<String, String> map) {
		// 将参数Key按字典顺序排序
		String[] sortedKeys = map.keySet().toArray(new String[]{});
		Arrays.sort(sortedKeys);

		// 生成规范化请求字符串
		StringBuilder canonicalizedQueryString = new StringBuilder();
		for (String key : sortedKeys) {
			canonicalizedQueryString.append("&").append(key).append("=").append(map.get(key));
		}
		// return canonicalizedQueryString.toString();
		return canonicalizedQueryString.substring(1).replaceAll("&", "!^!");
	}

	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";

	public static String generateString(int length) // 参数为返回随机数的长度
	{
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	/**
	 * KB转换为MB，保留2位小数
	 * 
	 * @param k
	 * @return
	 */
	public static String KB2MB(int k) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(k / 1024.0000);
	}

	/**
	 * 格式化数字，123456，返回123,456
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDouble(double d) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(d);
	}

	public static String str2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 流水号,组成规则：yyyyMMddHHmmss+5 位随机数
	 * 
	 * @return
	 */
	public static String giveTradeNo() {
		return date2Str(new Date(), "yyyyMMddHHmmss") + String.format("%05d", new Random(System.currentTimeMillis()).nextInt(100000));
	}

	/**
	 * 获取2个日期的相差天数
	 * 
	 * @param beginDateStr
	 *            yyyy-MM-dd
	 * @param endDateStr
	 *            yyyy-MM-dd
	 * @return
	 */
	public static int getDaySub(String beginDateStr, String endDateStr) {
		Long day = new Long(0);
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day.intValue();
	}

	/**
	 * 判断是否是long型
	 * 
	 * @param s
	 * @param defaultVal
	 *            若不能转换为Long,则返回默认值
	 * @return
	 */
	public static Long ifLong(String s, long defaultVal) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return defaultVal;
		}
	}

	/**
	 * 判断是否是Integer型
	 * 
	 * @param s
	 * @param defaultVal
	 *            若不能转换Integer,则返回默认值
	 * @return
	 */
	public static Integer ifInteger(String s, int defaultVal) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return defaultVal;
		}
	}

	/**
	 * 查找指定文件夹下面的文件
	 * 
	 * @param findPath
	 *            指定文件夹
	 * @param extName
	 *            指定文件后缀，null表示所有
	 * @return
	 */
	public static List<File> findFile(String findPath, String extName) {
		List<File> filelist = new ArrayList<File>();
		File dir = new File(findPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return filelist;
		}
		if (!isEmpty(extName) && !extName.startsWith(".")) {
			extName = "." + extName;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				filelist.addAll(findFile(files[i].getAbsolutePath(), extName));
			} else {
				String strFileName = files[i].getAbsolutePath().toLowerCase();
				if (isEmpty(extName)) {
					filelist.add(files[i]);
				} else if (strFileName.endsWith(extName.toLowerCase())) {
					filelist.add(files[i]);
				}
			}
		}
		return filelist;
	}

	/**
	 * 判断文件的编码格式
	 * 
	 * @param fileName
	 * @return 文件编码格式
	 * @throws Exception
	 */
	public static String codeString(String fileName) {
		return codeString(new File(fileName));
	}

	/**
	 * 判断文件的编码格式
	 * 
	 * @param file
	 * @return 文件编码格式
	 * @throws Exception
	 */
	public static String codeString(File file) {
		try {
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
			int p = (bin.read() << 8) + bin.read();
			String code = null;
			// 其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
			switch (p) {
				case 0xefbb :
					code = "UTF-8";
					break;
				case 0xfffe :
					code = "Unicode";
					break;
				case 0xfeff :
					code = "UTF-16BE";
					break;
				case 0x5c75 :
					code = "ANSI|ASCII";
					break;
				default :
					code = "GBK";
			}
			return code;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "UTF-8";
	}

	public static void setSysTime(Date d) {
		String osName = System.getProperty("os.name");
		String cmd = "";
		try {
			if (osName.matches("^(?i)Windows.*$")) {// Window 系统
				// 格式 HH:mm:ss
				cmd = "  cmd /c time " + date2Str(d, "HH:mm:ss");
				Runtime.getRuntime().exec(cmd);
				// 格式：yyyy-MM-dd
				cmd = " cmd /c date " + date2Str(d, "yyyy-MM-dd");
				Runtime.getRuntime().exec(cmd);
			} else {// Linux 系统
				// 格式：yyyyMMdd
				cmd = "  date -s " + date2Str(d, "yyyyMMdd");
				Runtime.getRuntime().exec(cmd);
				// 格式 HH:mm:ss
				cmd = "  date -s " + date2Str(d, "HH:mm:ss");
				Runtime.getRuntime().exec(cmd);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
