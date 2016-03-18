package dp.facade;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��5��7������3:09:00
 * @version 1.0
 */
public class Facade {
	LetterProcess letterProcessImpl = new LetterProcessImpl();
	
	public void send(String context,String address) {
		letterProcessImpl.writeLetter(context);
		letterProcessImpl.writeAddress(address);
		letterProcessImpl.send();
	}
}
