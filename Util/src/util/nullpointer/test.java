package util.nullpointer;

import java.util.List;

/**
 * @author <a href="mailto:zhuhao@189read.com.">朱豪</a>
 * 2015年5月5日上午10:45:27
 * @version 1.0
 */
public class test {
	public void set() {
		Person person = new Person();
		person.setAge(null);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Person person = new Person();
		person.setAge(null);
		person.setName(null);
		System.out.println("aa"+person.getAge());
	}

}
