package util.nullpointer;

import java.util.List;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��5��5������10:45:27
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
