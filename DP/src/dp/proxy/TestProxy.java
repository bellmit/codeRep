package dp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��27������4:18:10
 * @version 1.0
 */
public class TestProxy {
	public static void main(String[] args) {
		Bus bus = new Bus();
		InvocationHandler h = new TimeHandler(bus);
		Class<?> cls = bus.getClass();
		Moveable m = (Moveable)Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);
		m.move();
	}
}
