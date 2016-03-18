package dp.factoryMethod;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��7��2������3:19:31
 * @version 1.0
 */
public class NikeFactory implements ShoeFactory {

	@Override
	public Shoe getShoe() {
		return new NikeShoe();
	}

}
