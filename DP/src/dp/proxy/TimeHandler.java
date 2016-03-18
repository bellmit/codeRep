package dp.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��27������4:11:15
 * @version 1.0
 */
public class TimeHandler implements InvocationHandler {
	
	private Object target;
	
	public TimeHandler(Object target) {
		super();
		this.target = target;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Long startTime = System.currentTimeMillis();
		method.invoke(target);
		Long endTime = System.currentTimeMillis();
		System.out.println("��ʻ��ʱ��"+(endTime-startTime) + "ms");
		return null;
	}

}
