package dp.facade;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��5��7������3:10:33
 * @version 1.0
 */
public class Client {
	public static void main(String[] args) {
		Facade facade = new Facade();
		
		String context = "this is context";
		String address = "this is address";
		facade.send(context, address);
	}
}
