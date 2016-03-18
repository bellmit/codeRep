/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��1������3:17:34
 * @version 1.0
 */
package dp.singleton;
/*
 * ����ģʽSingleton
 * ����ģʽ
 * Ӧ�ó��ϣ���Щ����ֻ��Ҫһ�����㹻�ˣ���Ŵ�ʵۡ�����
 * ���ã���֤���Ӧ�ó�����ĳ��ʵ������ֻ��һ��
 * ���ͣ�����ģʽ������ģʽ
 */
public class Wife1 {
	//1.�����췽��˽�л����������ⲿֱ�Ӵ�������
	private Wife1(){		
	}
	
	//2.�������Ψһʵ��ʹ��private static����
	private static Wife1 instance=new Wife1();
	
	//3.�ṩһ�����ڻ�ȡʵ��ķ�����ʹ��public static����
	public static Wife1 getInstance(){
		return instance;
	}
}
