package dp.factoryMethod;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��2������3:20:22
 * @version 1.0
 */
public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShoeFactory factory = new NikeFactory();
		Shoe nikeShoe = factory.getShoe();
		nikeShoe.getName();
		
		factory = new AdFactory();
		Shoe adShoe = factory.getShoe();
		adShoe.getName();
	}

}
