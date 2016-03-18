package com.java.base.networkInterface;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年8月4日下午3:14:16
 * @version 1.0
 */
public class Network {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String Local_IP = getLocalAddress();
		System.out.println(Local_IP);
	}
	
    private static String getLocalAddress() {

        try {
            // 遍历网卡，查找一个非回路ip地址并返回
            final Enumeration<NetworkInterface> enumeration = NetworkInterface
                    .getNetworkInterfaces();
            InetAddress ipv6Address = null;
            while (enumeration.hasMoreElements()) {
                final NetworkInterface networkInterface = enumeration.nextElement();
                final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
                while (en.hasMoreElements()) {
                    final InetAddress address = en.nextElement();
                    if (!address.isLoopbackAddress() && address.isSiteLocalAddress()) {
                        if (address instanceof Inet6Address) {
                            ipv6Address = address;
                        } else {
                            // 优先使用ipv4
                            return normalizeHostAddress(address);
                        }
                    }

                }

            }
            // 没有ipv4，再使用ipv6
            if (ipv6Address != null) {
                return normalizeHostAddress(ipv6Address);
            }
            final InetAddress localHost = InetAddress.getLocalHost();
            return normalizeHostAddress(localHost);
        } catch (final Exception e) {
            return "127.0.0.1";
        }
    }
    
    private static String normalizeHostAddress(final InetAddress localHost) {
        if (localHost instanceof Inet6Address) {
            return "[" + localHost.getHostAddress() + "]";
        } else {
            return localHost.getHostAddress();
        }
    }

}
