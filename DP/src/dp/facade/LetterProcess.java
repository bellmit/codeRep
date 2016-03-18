package dp.facade;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��5��7������3:05:00
 * @version 1.0
 */
public interface LetterProcess {
	//д�ŵ�����
	public void writeLetter(String context);
	//д�ռ��˵�ַ
	public void writeAddress(String address);
	//����
	public void send();
}
