package dp.singleton;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��1������3:24:03
 * @version 1.0
 */
/*
 * ����ģʽ
 * ��𣺶���ģʽ���ص��Ǽ�����ʱ�Ƚ�������ʱ��ȡ������ٶȱȽϿ죬�̰߳�ȫ
 *      ����ģʽ���ص��Ǽ�����ʱ�ȽϿ죬������ʱ��ȡ������ٶȱȽ����̲߳���ȫ
 */
public class Wife2 {
	//1.�����췽ʽ˽�л������������ֱ�Ӵ�������
	private Wife2(){
	}
	
	//2.�������Ψһʵ��ʹ��private static����
	private static Wife2 instance;
	
	//3.�ṩһ�����ڻ�ȡʵ��ķ�����ʹ��public static����
	public static Wife2 getInstance(){
		if(instance==null){
			instance=new Wife2();
		}
		return instance;
	}
}
