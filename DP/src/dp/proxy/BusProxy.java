package dp.proxy;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��27������4:01:56
 * @version 1.0
 */
public class BusProxy {
	public BusProxy(Bus bus) {
		super();
		this.bus = bus;
	}

	private Bus bus;
	
	public void move() {
		Long startTime = System.currentTimeMillis();
		bus.move();
		
		Long endTime = System.currentTimeMillis();
		
		System.out.println("��ʻ��ʱ��" + (endTime-startTime) + "ms");
	}
}
