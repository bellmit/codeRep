package test.guyu.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.guyu.data.bean.User;
import com.guyu.data.bean.UserProperty;
import com.guyu.data.dao.IUserDao;
import com.guyu.data.dao.IUserPropertyDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./config/spring/spring.xml",
		"file:./config/spring/spring-db.xml",
		"file:./config/spring/spring-cache.xml"

})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
public class PropertyTest {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserPropertyDao propertyDao;

	@Test
	public void test1() {

		propertyDao.executeUpdate("delete from t_user_prop");
		int uid = 1;
		List<UserProperty> ups = new ArrayList<UserProperty>();

		for (int i = 0; i < 10000; i++) {
			UserProperty up2 = new UserProperty();
			up2.setPid(i);
			up2.setUid(uid);
			up2.setNum(1);
			ups.add(up2);
		}
		propertyDao.batchSave(ups);

		UserProperty up = propertyDao.getUserProperty(1, uid);
		if (up == null) {
			up = new UserProperty();
			up.setNum(2);
			up.setUid(uid);
			up.setPid(1);
			propertyDao.saveUserProperty(up);

		} else {

			up.setNum(up.getNum() + 1);
			propertyDao.updateUserProperty(up);

		}

		List<Map<String, Object>> list = propertyDao.find(
				"select id from UserProperty where uid = ?", 1);

		List<UserProperty> list2 = propertyDao.find(
				"select * from UserProperty where uid = ?", 1);

		List<UserProperty> list3 = propertyDao.find(
				"select * from t_user_prop where uid = ?", 1);

		List<UserProperty> list4 = propertyDao.find(
				"select * from t_user_prop where uid = ? and pid = ?", 1, 1);

		UserProperty list5 = (UserProperty) propertyDao.get(
				"select * from t_user_prop where uid = ? and pid = ?", 1, 1);

		List<UserProperty> list6 = propertyDao.find(UserProperty.class,
				"uid = ? and pid = ?", 1, 1);

		UserProperty list7 = propertyDao.get(UserProperty.class,
				"uid = ? and pid = ?", 1, 1);

		List<UserProperty> ups2 = propertyDao.getUserPropertys(uid);

		propertyDao.batchUpdate(ups2);

	}

}
