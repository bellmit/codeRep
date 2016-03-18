package dp.template;

/**
 * @author <a href="mailto:zhuhao@189read.com.">���</a>
 * 2015��4��8������5:54:18
 * @version 1.0
 */
public class JamesShoot extends ShootTemplate {
	private boolean defenceFlag;
	@Override
	protected void dribble() {
		System.out.println("ղķ˹����");
	}

	@Override
	protected void shoot() {
		System.out.println("ղķ˹Ͷ����");
	}

	public boolean isDefence() {
		return defenceFlag;
	}

	public void setDefenceFlag(boolean defenceFlag) {
		this.defenceFlag = defenceFlag;
	}

}
