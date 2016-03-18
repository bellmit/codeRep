package dp.proxy;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��27������4:06:46
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) {
		Bus bus = new Bus();
		BusProxy bp = new BusProxy(bus);
		bp.move();
		
	}
}
