package dp.adapter;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��13������4:46:22
 * @version 1.0
 */
public class NoteBook {
	public static void main(String[] args) {
		System.out.println("�½�һ����������");
		AdapterExtends adapterExtends = new AdapterExtends();
		adapterExtends.charge();
		
		System.out.println("�½�һ������������");
		Adapter adapter = new Adapter();
		adapter.charge();
	}
}
