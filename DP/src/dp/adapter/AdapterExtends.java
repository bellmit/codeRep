package dp.adapter;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��13������4:44:13
 * @version 1.0
 * ͨ��̳����Ϊ����������
 */
public class AdapterExtends extends ThreePlugImpl implements TwoPlug {
	@Override
	public void charge() {
		System.out.println("ͨ�����䣡");
		super.specificCharge();
	}
}
