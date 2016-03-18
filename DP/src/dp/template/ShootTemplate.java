package dp.template;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��8������5:42:04
 * @version 1.0
 */
public abstract class ShootTemplate {
	/*
	 * ����Ͷ����ģ�巽��
	 * ��װ���������๲ͬ��ѭ���㷨���
	 */
	public final void play() {
		//����1������
		dribble();
		if(isDefence()) {
			//����2������
			huang();
		}
		//����3��Ͷ��
		shoot();
		//����4���÷�
		point();
	}
	/**
	 * ����Ļ���������
	 */
	protected abstract void dribble();
	/**
	 * ����������
	 */
	private void huang() {
		System.out.println("����");
	}
	/**
	 * ����Ļ�����Ͷ��
	 */
	protected abstract void shoot();
	/**
	 * �������÷�
	 */
	private void point() {
		System.out.println("�÷�");
	}
	/**
	 * Hook, ���Ӻ����ṩһ��Ĭ�ϻ�յ�ʵ��
	 * ���������������о����Ƿ�ҹ��Լ���ιҹ�
	 * ѯ���û��Ƿ��з���
	 */
	protected boolean isDefence() {
		return true;
	}
}
