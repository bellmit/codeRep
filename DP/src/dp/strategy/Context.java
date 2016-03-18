package dp.strategy;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��6��16������2:56:01
 * @version 1.0
 */
public class Context {
	private Strategy strategy;
	
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void sort() {
		strategy.sort();
	}
}
