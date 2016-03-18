package dp.template;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��8������5:57:58
 * @version 1.0
 */
public class Coach {
	public static void main(String[] args) {
		KobeShoot Kobe = new KobeShoot();
		JamesShoot James = new JamesShoot();
		James.setDefenceFlag(false);
		Kobe.play();
		James.play();
	}
}
