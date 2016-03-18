package dp.adapter;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��13������4:48:08
 * @version 1.0
 * ͨ��������Ϊ������������
 */
public class Adapter implements TwoPlug {
	ThreePlugImpl threePlugImpl = new ThreePlugImpl();
	@Override
	public void charge() {
		System.out.println("ͨ������..");
		threePlugImpl.specificCharge();
	}

}
