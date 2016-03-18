package dp.factoryMethod;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��2������3:20:00
 * @version 1.0
 */
public class AdFactory implements ShoeFactory {

	@Override
	public Shoe getShoe() {
		return new AdShoe();
	}

}
