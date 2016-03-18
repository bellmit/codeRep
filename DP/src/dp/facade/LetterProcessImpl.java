package dp.facade;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��5��7������3:07:41
 * @version 1.0
 */
public class LetterProcessImpl implements LetterProcess {

	@Override
	public void writeLetter(String context) {
		System.out.println("write context:" + context);
	}

	@Override
	public void writeAddress(String address) {
		System.out.println("write address:" + address);
	}

	@Override
	public void send() {
		System.out.println("send letter!");
	}

}
